package com.atguigu.gmall.cart.service.api.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.cart.mapper.CartItemMapper;
import com.atguigu.gmall.entity.OmsCartItem;
import com.atguigu.gmall.service.api.CartService;
import com.atguigu.gmall.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public OmsCartItem isCartExist(String productSkuId, String memberId) {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setProductSkuId(productSkuId);
        omsCartItem.setMemberId(memberId);
        OmsCartItem omsCartItem1 = cartItemMapper.selectOne(omsCartItem);
        return omsCartItem1;
    }

    @Override
    public void editOmsCartItem(OmsCartItem omsCartItemDB) {
        //修改数据库中购物车的商品数量
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setQuantity(omsCartItemDB.getQuantity());
        Example example = new Example(OmsCartItem.class);
        example.createCriteria().andEqualTo(omsCartItemDB.getId());
        cartItemMapper.updateByExampleSelective(omsCartItem,example);

        //同步缓存数据
        OmsCartItem omsCartItemNEW = new OmsCartItem();
        omsCartItemNEW.setMemberId(omsCartItemDB.getMemberId());
        List<OmsCartItem> omsCartItems = cartItemMapper.select(omsCartItemNEW);
        syncCartCache(omsCartItems);
    }



    @Override
    public void addOmsCartItem(OmsCartItem omsCartItem) {
        //添加购物车
        cartItemMapper.insert(omsCartItem);

        //同步缓存数据
    }

    //同步缓存方法
    private void syncCartCache(List<OmsCartItem> omsCartItems) {
        Jedis jedis = redisUtil.getJedis();
        try{
            for (OmsCartItem omsCartItem : omsCartItems) {
                String cacheCartKey="memberId:"+omsCartItem.getMemberId()+":cart";
            }
        }finally {
            jedis.close();
        }
    }
}
