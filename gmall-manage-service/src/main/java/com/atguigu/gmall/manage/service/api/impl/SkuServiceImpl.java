package com.atguigu.gmall.manage.service.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.entity.*;
import com.atguigu.gmall.manage.mapper.SkuAttrValueMapper;
import com.atguigu.gmall.manage.mapper.SkuImageMapper;
import com.atguigu.gmall.manage.mapper.SkuInfoMapper;
import com.atguigu.gmall.manage.mapper.SkuSaleAttrValueMapper;
import com.atguigu.gmall.service.api.SkuService;
import com.atguigu.gmall.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.beans.Beans;
import java.util.List;
import java.util.UUID;

@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuInfoMapper skuInfoMapper;
    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;
    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;
    @Autowired
    private SkuImageMapper skuImageMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        //添加sku库存单元商品信息
        skuInfoMapper.insertSelective(pmsSkuInfo);
        //得到商品信息id
        String skuInfoId = pmsSkuInfo.getId();
        //保存sku对应的平台属性
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuInfoId);
            skuAttrValueMapper.insert(pmsSkuAttrValue);
        }
        //保存sku对应销售属性
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuInfoId);
            skuSaleAttrValueMapper.insert(pmsSkuSaleAttrValue);
        }
        //保存sku的图片
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(skuInfoId);
            skuImageMapper.insert(pmsSkuImage);
        }
    }

    @Override
    public PmsSkuInfo getSkuById(String skuId) {
        System.out.println(Thread.currentThread().getName()+"进行商品"+skuId+"详情请求");
        //先从redis缓存中查询
        Jedis jedis = redisUtil.getJedis();
        try {
            String skuKey="sku" + skuId + "info";
            String skuInfoJsonStr = jedis.get(skuKey);
            //结果不为空直接返回
            if(skuInfoJsonStr!=null){
                System.out.println(Thread.currentThread().getName()+"从缓存中获取数据");
                PmsSkuInfo skuInfoJson = JSON.parseObject(skuInfoJsonStr,PmsSkuInfo.class);
                return skuInfoJson;
            }else{
                //创建一个key到redis中,分布式锁,解决缓存击穿
                String token ="sku"+skuId+"lock";
                String uuid=UUID.randomUUID().toString();
                String ok = jedis.set(token, uuid, "nx", "px", 30000);
                if(StringUtils.isNotBlank(ok)&&ok.equals("OK")){
                    System.out.println(Thread.currentThread().getName()+"获得"+skuId+"分布式锁");
                    //为空,查询mysql数据库
                    PmsSkuInfo pmsSkuInfo = returnSkuInfoById(skuId);
                    //更新redis中对应的缓存
                   /* try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    skuInfoJsonStr = JSON.toJSONString(pmsSkuInfo);
                    jedis.set(skuKey,skuInfoJsonStr);
                    //将token删除前,判断当前的uuid是否和要删除的token值一致
                    String tokenUuId = jedis.get(token);
                    if(tokenUuId.equals(uuid)){
                        jedis.del(token);
                        System.out.println(Thread.currentThread().getName()+"请求mysql成功,归还"+skuId+"分布式锁");
                        //返回结果
                        return pmsSkuInfo;
                    }
                }else{
                    System.out.println(Thread.currentThread().getName()+"没有获得分布式锁等待5秒后回旋");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return getSkuById(skuId);
                }

            }
        }
        finally {
            jedis.close();
        }
        return null;

    }

/*    @Override
    public PmsSkuInfo getSkuById(String skuId) {
        System.out.println(Thread.currentThread().getName() + "进行商品" + skuId + "详情请求");
        //先从redis缓存中查询
        Jedis jedis = redisUtil.getJedis();

        try {
            String skuKey = "sku" + skuId + "info";
            String skuInfoJsonStr = jedis.get(skuKey);
            //结果不为空直接返回
            if (skuInfoJsonStr != null) {
                System.out.println(Thread.currentThread().getName() + "从缓存中获取数据");
                PmsSkuInfo skuInfoJson = JSON.parseObject(skuInfoJsonStr, PmsSkuInfo.class);
                return skuInfoJson;
            } else {
                //创建一个key到redis中,分布式锁,解决缓存击穿
                String token = "sku" + skuId + "lock";
                RLock lock = redissonClient.getLock(token);
                try {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + "获得" + skuId + "分布式锁");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //为空,查询mysql数据库
                    PmsSkuInfo pmsSkuInfo = returnSkuInfoById(skuId);
                    //更新redis中对应的缓存
                    skuInfoJsonStr = JSON.toJSONString(pmsSkuInfo);
                    jedis.set(skuKey, skuInfoJsonStr);
                    return pmsSkuInfo;
                } finally {
                    lock.unlock();
                }
            }
        } finally {
            jedis.close();
        }


    }*/


    //根据skuid查询数据库中对应的skuInfo信息
    public PmsSkuInfo returnSkuInfoById(String skuId) {
        PmsSkuInfo pmsSkuInfo = skuInfoMapper.selectByPrimaryKey(skuId);
        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setSkuId(skuId);
        List<PmsSkuImage> PmsSkuImages = skuImageMapper.select(pmsSkuImage);
        pmsSkuInfo.setSkuImageList(PmsSkuImages);
        return pmsSkuInfo;
    }


    //查询当前sku商品的spu下所有的销售属性和当前选中的商品销售属性
    @Override
    public List<PmsProductSaleAttr> getspuSaleAttrListCheckBySku(String productId, String skuId) {
        return skuSaleAttrValueMapper.selectSpuSaleAttrListCheckBySku(productId, skuId);
    }

    //查询当前sku商品的spu下对应的商品和商品销售属性
    @Override
    public List<PmsSkuInfo> getSkuInfoListBySpuId(String productId) {
     /*   PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setProductId(productId);
        List<PmsSkuInfo> skuInfos = skuInfoMapper.select(pmsSkuInfo);
        for (PmsSkuInfo skuInfo : skuInfos) {
            String skuId = skuInfo.getId();
            PmsSkuSaleAttrValue pmsSkuSaleAttrValue = new PmsSkuSaleAttrValue();
            pmsSkuSaleAttrValue.setSkuId(skuId);
            List<PmsSkuSaleAttrValue> skuSaleAttrValues = skuSaleAttrValueMapper.select(pmsSkuSaleAttrValue);
            skuInfo.setSkuSaleAttrValueList(skuSaleAttrValues);
        }
        return skuInfos;*/
        List<PmsSkuInfo> pmsSkuInfos = skuInfoMapper.selectSkusByProductId(productId);
        return pmsSkuInfos;
    }

    @Override
    public List<PmsSkuInfo> getSkuAll() {
        List<PmsSkuInfo> pmsSkuInfos = skuInfoMapper.selectAll();
        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfos) {
            String id = pmsSkuInfo.getId();
            PmsSkuAttrValue pmsSkuAttrValue = new PmsSkuAttrValue();
            pmsSkuAttrValue.setSkuId(id);
            List<PmsSkuAttrValue> select = skuAttrValueMapper.select(pmsSkuAttrValue);
            pmsSkuInfo.setSkuAttrValueList(select);
        }
        return pmsSkuInfos;
    }
}
