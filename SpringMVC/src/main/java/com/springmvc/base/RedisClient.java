package com.springmvc.base;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis 客户端封装
 */
public interface RedisClient {
    /**
    *redis 连接池
    */
    void setPool(JedisPool pool);

    /**
    * 获取jedis
    * @return
    */
    Jedis getResource();

    /**
    * 关闭连接
    * @param jedis
    */
    void disconnect(Jedis jedis);

    /**
     * 关闭连接
     * */
    void returnResource(Jedis jedis);
}
