package com.zzxx.travel.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

public class JedisTest {
    @Test
    public void jedisTest() {
        Jedis jedis = new Jedis("192.168.56.101", 6379);
        jedis.set("username", "wm");
        String name = jedis.get("username");

        jedis.zadd("sortedset", 90, "w");
        Set<String> sortedset = jedis.zrange("sortedset", 0, -1);

        System.out.println(name);
        sortedset.stream().forEach(System.out::println);

        // 关闭jedis
        jedis.close();
    }

    @Test
    public void jedisPoolTest() {
        // 创建连接池配置对象
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 设置最大连接数
        poolConfig.setMaxTotal(30);
        // 设置最大连接空闲数
        poolConfig.setMaxIdle(10);

        // 获得连接池
        JedisPool jedisPool = new JedisPool(poolConfig, "192.168.56.101", 6379);
        // 获得Jedis
        Jedis jedis = jedisPool.getResource();
        jedis.set("age", "20");

        String age = jedis.get("age");
        System.out.println(age);

        // 释放资源
        jedis.close();
        // 虚拟机关闭时 释放pool资源
        jedisPool.close();
    }
}
