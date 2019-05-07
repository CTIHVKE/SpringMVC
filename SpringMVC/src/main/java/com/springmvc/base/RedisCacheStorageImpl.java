package com.springmvc.base;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * redis 缓存存储器实现
 * */
public class RedisCacheStorageImpl<V> implements RedisCacheStorage<RedisKeyEnum, String, V> {

    /**
     * redis 客户端
     * */
    private RedisClient redisClient;

    /**
     * 默认过时时间
     * */
    private static final int EXPRIE_TIME = 3600*24;

    public void setRedisClient(RedisClient redisClient){
        this.redisClient = redisClient;
    }

    @Override
    public boolean set(RedisKeyEnum key, V value) {
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
    public boolean set(RedisKeyEnum key, V value, int exp) {
        Jedis jedis = null;
        //将key 和 value 转换成Json 对象
        String jKey = JSON.toJSONString(key);
        String jValue = JSON.toJSONString(value);
        //操作是否成功
        boolean isSucess = true;
        if(StringUtils.isEmpty(jKey)){
            System.out.println("key is empty!");
            return false;
        }
        try {
            //获取客户端对象
            jedis = redisClient.getResource();
            //执行插入
            jedis.setex(jKey,exp,jValue);
        }catch (JedisException e){
            System.out.println("客户端连接失败！");
            isSucess = false;
            if(null != jedis){
                //释放jedis对象
                //redisClient.brokenResource(jedis);
                redisClient.close(jedis);
            }
            return false;
        }finally {
            if(isSucess){
                //返还连接池
                //redisClient.returnResource(jedis);
                redisClient.close(jedis);
            }
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(RedisKeyEnum key) {
        Jedis jedis = null;
        //转换成json 对象
        String jKey = JSON.toJSONString(key);
        V jValue = null;
        //key 不能为空
        if(StringUtils.isEmpty(jKey)){
            System.out.println("key 为空！");
            return null;
        }
        try {
            jedis = redisClient.getResource();
            //执行查询
            String value = jedis.get(jKey);
            if(StringUtils.isEmpty(value)) {
                return null;
            }
            else {
                jValue = (V)JSON.toJSONString(value);
            }
            //返还连接池
            //redisClient.returnResource(jedis);
            redisClient.close(jedis);
        }catch (JedisException e){
            System.out.println("redis 客户端连接失败！");
            if(null != jedis){
                //释放jedis 对象
                //redisClient.brokenResource(jedis);
                redisClient.close(jedis);
            }
        }
        return jValue;
    }

    /**
     * 删除redis 库中的数据
     * */
    @SuppressWarnings("finally")
    @Override
    public boolean remove(RedisKeyEnum key) {
        Jedis jedis = null;
        String jKey = JSON.toJSONString(key);

        boolean isSucess = true;
        if(StringUtils.isEmpty(jKey)){
            System.out.println("key 为空！");
            return false;
        }
        try {
            jedis = redisClient.getResource();
            //执行删除
            jedis.del(jKey);
        }catch (JedisException e){
            System.out.println("客户端连接失败！");
            isSucess = false;
            if(null != jedis){
                //释放jedis 对象
                //redisClient.brokenResource(jedis);
                redisClient.close(jedis);
            }
            return false;
        }finally {
            if(isSucess){
                //返还接连池
                //redisClient.returnResource(jedis);
                redisClient.close(jedis);
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
    public boolean hset(RedisKeyEnum cacheKey, String key, V value) {
        Jedis jedis =null;
        //将key 和value  转换成 json 对象
        String jKey =JSON.toJSONString(key);
        String jCacheKey =JSON.toJSONString(cacheKey);
        String jValue =JSON.toJSONString(value);
        //操作是否成功
        boolean isSucess =true;
        if(StringUtils.isEmpty(jCacheKey)){
            System.out.println("cacheKey 为空");
            return false;
        }
        try {
            jedis =redisClient.getResource();
            //执行插入哈希
            jedis.hset(jCacheKey, jKey, jValue);
        } catch (JedisException e) {
            System.out.println("连接客户端失败！");
            isSucess =false;
            if(null !=jedis){
                //释放jedis 对象
                //redisClient.brokenResource(jedis);
                redisClient.close(jedis);
            }
            return false;
        }finally{
            if (isSucess) {
                //返还连接池
                //redisClient.returnResource(jedis);
                redisClient.close(jedis);
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
    public V hget(RedisKeyEnum cacheKey, String key) {
        Jedis jedis =null;
        //将key 和value  转换成 json 对象
        String jKey =JSON.toJSONString(key);
        String jCacheKey =JSON.toJSONString(cacheKey);
        V jValue =null;
        if(StringUtils.isEmpty(jCacheKey)){
            System.out.println("cacheKey 为空！");
            return null;
        }
        try {
            //获取客户端对象
            jedis =redisClient.getResource();
            //执行查询
            String value =  jedis.hget(jCacheKey, jKey);
            //判断值是否非空
            if(StringUtils.isEmpty(value)){
                return null;
            }else{
                jValue= (V) JSON.toJSONString(value);
            }
            //返还连接池
            //redisClient.returnResource(jedis);
            redisClient.close(jedis);
        } catch (JedisException e) {
            System.out.println("连接客户端失败！");
            if(null !=jedis){
                //释放jedis 对象
                //redisClient.brokenResource(jedis);
                redisClient.close(jedis);
            }
        }
        return jValue;
    }

    /**
     * 获取哈希类型的数据
     *
     * @param cacheKey
     * @return
     */
    @Override
    public Map<String, V> hget(RedisKeyEnum cacheKey) {
        String jCacheKey = JSON.toJSONString(cacheKey);
        //非空校验
        if(StringUtils.isEmpty(jCacheKey)){
            System.out.println("cacheKey 为空！");
            return null;
        }
        Jedis jedis =null;
        Map<String,V> result =null;
        try {
            jedis =redisClient.getResource();
            //获取列表集合
            Map<String,String> map = jedis.hgetAll(jCacheKey);

            if(null !=map){
                for(Map.Entry<String, String> entry : map.entrySet()){
                    if(result ==null){
                        result =new HashMap<String, V>();
                    }
                    result.put((String) JSON.toJSONString(entry.getKey()), (V)JSON.toJSONString(entry.getValue()));
                }
            }
        } catch (JedisException e) {
            System.out.println("客户端连接失败！");
            if(null !=jedis){
                //释放jedis 对象
                //redisClient.brokenResource(jedis);
                redisClient.close(jedis);
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
    public boolean lpush(RedisKeyEnum cacheKey, V value) {
        Jedis jedis =null;
        //将key 和value  转换成 json 对象
        String jCacheKey =JSON.toJSONString(cacheKey);
        String jValue =JSON.toJSONString(value);
        //操作是否成功
        boolean isSucess =true;
        if(StringUtils.isEmpty(jCacheKey)){
            System.out.println("cacheKey 为空");
            return false;
        }
        try {
            jedis =redisClient.getResource();
            //执行插入列表
            jedis.lpush(jCacheKey, jValue);
        } catch (JedisException e) {
            System.out.println("连接客户端失败！");
            isSucess =false;
            if(null !=jedis){
                //释放jedis 对象
                //redisClient.brokenResource(jedis);
                redisClient.close(jedis);
            }
            return false;
        }finally{
            if (isSucess) {
                //返还连接池
                //redisClient.returnResource(jedis);
                redisClient.close(jedis);
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
    @SuppressWarnings("unchecked")
    @Override
    public Long llen(RedisKeyEnum cacheKey) {
        Jedis jedis =null;
        //将key 和value  转换成 json 对象
        String jCacheKey =JSON.toJSONString(cacheKey);
        Long len = null;
        if(StringUtils.isEmpty(jCacheKey)){
            System.out.println("cacheKey 为空！");
            return null;
        }
        try {
            //获取客户端对象
            jedis =redisClient.getResource();
            //执行查询
            len =  jedis.llen(cacheKey.toString());
            //返还连接池
            //redisClient.returnResource(jedis);
            redisClient.close(jedis);
        } catch (JedisException e) {
            System.out.println("连接客户端失败！");
            if(null !=jedis){
                //释放jedis 对象
                //redisClient.brokenResource(jedis);
                redisClient.close(jedis);
            }
        }
        return len;
    }

    /**
     * 获取列表类型的数据
     *
     * @param cacheKey
     * @return
     */
    @Override
    public List<String> lget(RedisKeyEnum cacheKey,int start,int end) {
        Jedis jedis =null;
        //将key 和value  转换成 json 对象
        String jCacheKey =JSON.toJSONString(cacheKey);
        List<String> result = null;
        if(StringUtils.isEmpty(jCacheKey)){
            System.out.println("cacheKey 为空！");
            return null;
        }
        try {
            //获取客户端对象
            jedis =redisClient.getResource();
            //执行查询
            result =  jedis.lrange(cacheKey.toString(),start,end);
            System.out.println(cacheKey + "lget-result-len:" + result.size());
            //返还连接池
            //redisClient.returnResource(jedis);
            redisClient.close(jedis);
        } catch (JedisException e) {
            System.out.println("连接客户端失败！");
            if(null !=jedis){
                //释放jedis 对象
                //redisClient.brokenResource(jedis);
                redisClient.close(jedis);
            }
        }
        return result;
    }


}
