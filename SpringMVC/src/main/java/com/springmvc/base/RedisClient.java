package com.springmvc.base;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis 客户端封装
 */
public class RedisClient {

    private JedisPool pool;

    /**
    *redis 连接池
    */
    public void setPool(JedisPool pool){
        this.pool = pool;
    }

    /**
    * 获取jedis
    * @return
    */
    public Jedis getResource(){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
        }catch (Exception e){
            System.out.println("获取jedis失败！");
        }
        return jedis;
    }

    /**
    * 关闭连接
    * @param jedis
    */
    public void disconnect(Jedis jedis){
        jedis.disconnect();
    }

    /**
     * 关闭连接
     * */
    public void close(Jedis jedis){
        if(null != jedis){
            try {
                jedis.close();
            }catch (Exception e){
                System.out.println("连接池关闭失败！");
            }
        }
    }

    /**
    * 将jedis返还连接池
    * @param jedis
    */
    public void returnResource(Jedis jedis){
        if(null != jedis){
            try {
                jedis.close();
                //pool.returnResource(jedis);
            }catch (Exception e){
                System.out.println("返还连接池失败！");
            }
        }
    }

    /**
    * 无法返还jedispool,释放jedis客户端对象
    * @param jedis
    */
    public void brokenResource(Jedis jedis){
        if(jedis != null){
            try {
                jedis.close();
                //pool.returnBrokenResource(jedis);
            }catch (Exception e){
                System.out.println("释放jedis客户端失败！");
            }
        }
    }
}
