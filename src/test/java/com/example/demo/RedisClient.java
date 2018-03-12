package com.example.demo;

import org.apache.log4j.Logger;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class RedisClient {


    private static Logger log = Logger.getLogger(RedisClient.class.getClass());
    private Jedis jedis;//非切片额客户端连接
    private JedisPool jedisPool;//非切片连接池
    private ShardedJedis shardedJedis;//切片额客户端连接
    private ShardedJedisPool shardedJedisPool;//切片连接池

    public RedisClient()
    {
        initialPool();
        initialShardedPool();
        shardedJedis = shardedJedisPool.getResource();
        jedis = jedisPool.getResource();


    }

    /**
     * 初始化非切片池
     */
    private void initialPool()
    {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(10001);
        config.setTestOnBorrow(false);

        jedisPool = new JedisPool(config,"127.0.0.1",6379);
    }

    /**
     * 初始化切片池
     */
    private void initialShardedPool()
    {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(10001);
        config.setTestOnBorrow(false);
        // slave链接
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        shards.add(new JedisShardInfo("127.0.0.1", 6379, "master"));

        // 构造池
        shardedJedisPool = new ShardedJedisPool(config, shards);
    }

    public void show() {
        KeyOperate();
        StringOperate();
        ListOperate();
        SetOperate();
        SortedSetOperate();
        HashOperate();
        jedisPool.returnResource(jedis);
        shardedJedisPool.returnResource(shardedJedis);
    }

    private void KeyOperate() {
        System.out.println("======================key==========================");

        System.out.println("新增key001,value001键值对："+shardedJedis.set("key001", "value001"));


        for(int i = 0; i<1000000000; i++)
        {
            shardedJedis.set("sd"+Integer.toString(i), "sd"+Integer.toString(i+1));
            if(i%100000 == 0)
            {
               log.info(i+ "sd");
            }
        }

    }

    private void StringOperate() {
        // 。。。
    }

    private void ListOperate() {
        // 。。。
    }

    private void SetOperate() {
      //   。。。
    }

    private void SortedSetOperate() {
       //  。。。
    }

    private void HashOperate() {
     //    。。。
    }
}

