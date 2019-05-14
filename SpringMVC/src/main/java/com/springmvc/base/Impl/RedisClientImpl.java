package com.springmvc.base.Impl;

import com.springmvc.base.RedisClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis 客户端封装
 */
public class RedisClientImpl implements RedisClient {

    private JedisPool pool;

    /**
     *redis 连接池
     */
    @Override
    public void setPool(JedisPool pool){
        this.pool = pool;
    }

    /**
     * 获取jedis
     * @return
     */
    @Override
    public Jedis getResource(){
        Jedis jedis = null;
        try{
            jedis = this.pool.getResource();
        }catch (Exception e){
            System.out.println("获取jedis失败！");
        }
        return jedis;
    }

    /**
     * 关闭连接
     * @param jedis
     */
    @Override
    public void disconnect(Jedis jedis){
        jedis.disconnect();
    }

    /**
     * 关闭连接
     * */
    @Override
    public void returnResource(Jedis jedis){
        if(null != jedis){
            try {
                jedis.close();
            }catch (Exception e){
                System.out.println("连接池关闭失败！");
            }
        }
    }
}
