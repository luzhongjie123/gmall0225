package com.atguigu.gmall;

import com.atguigu.gmall.entity.PmsSkuInfo;
import com.atguigu.gmall.entity.PmsSkuSaleAttrValue;
import com.atguigu.gmall.manage.mapper.SkuInfoMapper;
import com.atguigu.gmall.manage.mapper.SkuSaleAttrValueMapper;
import com.atguigu.gmall.service.api.SkuService;
import com.atguigu.gmall.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallManageServiceApplicationTests {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SkuService skuService;
    @Autowired
    private SkuInfoMapper skuInfoMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void contextLoads() {
        System.out.println(dataSource);
    }

    @Test
    public void contextLoads2() {
        List<PmsSkuInfo> pmsSkuInfos = skuInfoMapper.selectSkusByProductId("76");
        System.out.println(pmsSkuInfos);
    }

    @Test
    public void redisTest(){
        Jedis jedis = redisUtil.getJedis();
        String ping = jedis.ping();
        System.out.println(ping);
    }

}
