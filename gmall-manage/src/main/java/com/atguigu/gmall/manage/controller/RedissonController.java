package com.atguigu.gmall.manage.controller;

import com.atguigu.gmall.config.RedisConfig;
import com.atguigu.gmall.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

@Controller
public class RedissonController {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    RedissonClient redissonClient;

    @ResponseBody
    @RequestMapping("testItem")
    public String testItem(){
        String key=null;
        Jedis jedis=null;
        RLock lock = redissonClient.getLock("anyLock");
        lock.lock();
        try {
             jedis = redisUtil.getJedis();
             key = jedis.get("key");
            if(StringUtils.isBlank(key)){
                key="1";
            }
            key=Integer.parseInt(key)+1+"";
            jedis.set("key",key);
            System.out.println(key);

        } finally {
            jedis.close();
            lock.unlock();
        }
        return  key;
    }
}
