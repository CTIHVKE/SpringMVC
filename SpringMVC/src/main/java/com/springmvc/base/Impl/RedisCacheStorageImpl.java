package com.springmvc.base.Impl;

import com.springmvc.base.RedisCacheStorage;
import com.springmvc.base.RedisClient;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;
import java.util.Map;

/**
 * redis 缓存存储器实现
 * */
public class RedisCacheStorageImpl implements RedisCacheStorage<byte[], byte[], byte[]> {

    /**
     * redis 客户端
     * */
    private RedisClient redisClient;
    Jedis jedis = null;

    /**
     * 默认过时时间
     * */
    private static final int EXPRIE_TIME = 3600*24;

    public void setRedisClient(RedisClient redisClient){
        this.redisClient = redisClient;
    }

    @Override
    public boolean set(byte[] key, byte[] value) {
        //设置默认过时时间
        return set(key,value,EXPRIE_TIME);
    }

    /**
     * @param key
     * @param value
     * @param exp   过期时间 s
     */
    @SuppressWarnings("finally")
    @Override
    public boolean set(byte[] key, byte[] value, int exp) {
        //操作是否成功
        boolean isSucess = true;
        if(key.length <= 0){
            System.out.println("key is empty!");
            return false;
        }
        try {
            //获取客户端对象
            jedis = redisClient.getResource();
            //执行插入
            jedis.setex(key,exp,value);
        }catch (JedisException e){
            System.out.println("客户端连接失败！");
            isSucess = false;
            if(null != jedis){
                //释放jedis对象
                redisClient.returnResource(jedis);
            }
            return false;
        }finally {
            if(isSucess){
                //返还连接池
                redisClient.returnResource(jedis);
            }
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public byte[] get(byte[] key) {
        //key 不能为空
        if(StringUtils.isEmpty(key)){
            System.out.println("key 为空！");
            return null;
        }
        byte[] value = null;
        try {
            jedis = redisClient.getResource();
            //执行查询
            value = jedis.get(key);
            //返还连接池
            redisClient.returnResource(jedis);
        }catch (JedisException e){
            System.out.println("redis 客户端连接失败！");
            if(null != jedis){
                //释放jedis 对象
                redisClient.returnResource(jedis);
            }
        }
        return value;
    }

    @SuppressWarnings("finally")
    @Override
    public boolean remove(byte[] key) {
        boolean isSucess = true;
        if(key.length <= 0){
            System.out.println("key 为空！");
            return false;
        }
        try {
            jedis = redisClient.getResource();
            //执行删除
            jedis.del(key);
        }catch (JedisException e){
            System.out.println("客户端连接失败！");
            isSucess = false;
            if(null != jedis){
                //释放jedis 对象
                redisClient.returnResource(jedis);
            }
            return false;
        }finally {
            if(isSucess){
                //返还接连池
                redisClient.returnResource(jedis);
            }
            return true;
        }
    }

    /**
     * 设置哈希类型数据到redis 数据库
     *
     * @param cacheKey 可以看做一张表
     * @param key      表字段
     * @param value
     * @return
     */
    @SuppressWarnings("finally")
    @Override
    public boolean hset(byte[] cacheKey, byte[] key, byte[] value) {
        //操作是否成功
        boolean isSucess =true;
        if(cacheKey.length <= 0){
            System.out.println("cacheKey 为空");
            return false;
        }
        try {
            jedis =redisClient.getResource();
            //执行插入哈希
            jedis.hset(cacheKey, key, value);
        } catch (JedisException e) {
            System.out.println("连接客户端失败！");
            isSucess =false;
            if(null !=jedis){
                //释放jedis 对象
                redisClient.returnResource(jedis);
            }
            return false;
        }finally{
            if (isSucess) {
                //返还连接池
                redisClient.returnResource(jedis);
            }
            return true;
        }
    }

    /**
     * 获取哈希表数据类型的值
     *
     * @param cacheKey
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public byte[] hget(byte[] cacheKey, byte[] key) {
        byte[] value = null;
        if(cacheKey.length <= 0){
            System.out.println("cacheKey 为空！");
            return null;
        }
        try {
            //获取客户端对象
            jedis =redisClient.getResource();
            //执行查询
            value =  jedis.hget(cacheKey, key);
            //返还连接池
            redisClient.returnResource(jedis);
        } catch (JedisException e) {
            System.out.println("连接客户端失败！");
            if(null !=jedis){
                //释放jedis 对象
                redisClient.returnResource(jedis);
            }
        }
        return value;
    }

    /**
     * 获取哈希类型的数据
     *
     * @param cacheKey
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<byte[], byte[]> hget(byte[] cacheKey) {
        //非空校验
        if(StringUtils.isEmpty(cacheKey)){
            System.out.println("cacheKey 为空！");
            return null;
        }
        Map<byte[], byte[]> result =null;
        try {
            jedis =redisClient.getResource();
            //获取列表集合
            result = jedis.hgetAll(cacheKey);
        } catch (JedisException e) {
            System.out.println("客户端连接失败！");
            if(null !=jedis){
                //释放jedis 对象
                redisClient.returnResource(jedis);
            }
        }
        return result;
    }

    /**
     * 设置列表类型数据到redis 数据库
     *
     * @param cacheKey 可以看做一张表
     * @param value
     * @return
     */
    @SuppressWarnings("finally")
    @Override
    public boolean lpush(byte[] cacheKey, byte[] value) {
        //操作是否成功
        boolean isSucess =true;
        if(cacheKey.length <= 0){
            System.out.println("cacheKey 为空");
            return false;
        }
        try {
            jedis =redisClient.getResource();
            //执行插入列表
            jedis.lpush(cacheKey, value);
        } catch (JedisException e) {
            System.out.println("连接客户端失败！");
            isSucess =false;
            if(null !=jedis){
                //释放jedis 对象
                redisClient.returnResource(jedis);
            }
            return false;
        }finally{
            if (isSucess) {
                //返还连接池
                redisClient.returnResource(jedis);
            }
            return true;
        }
    }

    /**
     * 获取列表数据类型的值
     *
     * @param cacheKey
     * @return
     */
    @SuppressWarnings("finally")
    @Override
    public Long llen(byte[] cacheKey) {
        Long len = null;
        if(cacheKey.length <= 0){
            System.out.println("cacheKey 为空！");
            return null;
        }
        try {
            //获取客户端对象
            jedis =redisClient.getResource();
            //执行查询
            len =  jedis.llen(cacheKey);
            //返还连接池
            redisClient.returnResource(jedis);
        } catch (JedisException e) {
            System.out.println("连接客户端失败！");
            if(null !=jedis){
                //释放jedis 对象
                redisClient.returnResource(jedis);
            }
        }
        return len;
    }

    /**
     * 获取列表类型的数据
     *
     * @param cacheKey
     * @param start
     * @param end
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<byte[]> lget(byte[] cacheKey, int start, int end) {
        List<byte[]> result = null;
        if(cacheKey.length <= 0){
            System.out.println("cacheKey 为空！");
            return null;
        }
        try {
            //获取客户端对象
            jedis =redisClient.getResource();
            //执行查询
            result =  jedis.lrange(cacheKey,start,end);
//            System.out.println(cacheKey + "lget-result-len:" + result.size());
            //返还连接池
            redisClient.returnResource(jedis);
        } catch (JedisException e) {
            System.out.println("连接客户端失败！");
            if(null !=jedis){
                //释放jedis 对象
                redisClient.returnResource(jedis);
            }
        }
        return result;
    }
}
