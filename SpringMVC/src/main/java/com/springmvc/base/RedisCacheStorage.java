package com.springmvc.base;

import java.util.List;
import java.util.Map;

/**
* 缓存存储接口
* @param <K> key
* @param<V> value
* */
public interface RedisCacheStorage<K, V> {
    boolean set(K key,V value);

    /**
    * @param exp 过期时间 s
    * */
    boolean set(K key,V value, int exp);

    V get(K key);

    boolean remove(K key);

    /**
    * 设置哈希类型数据到redis 数据库
    * @param cacheKey 可以看做一张表
    * @param key   表字段
    * @param value
    * @return
    */
    boolean hset(String cacheKey,K key,V value);

    /**
     * 获取哈希表数据类型的值
     * @param cacheKey
     * @param key
     * @return
     */
    V hget(String cacheKey,K key);

    /**
     * 获取哈希类型的数据
     * @param cacheKey
     * @return
     */
    Map<K,V> hget(String cacheKey);


    /**
     * 设置列表类型数据到redis 数据库
     * @param cacheKey 可以看做一张表
     * @param key   表字段
     * @param value
     * @return
     */
    boolean lpush(String cacheKey,V value);

    /**
     * 获取列表数据类型的值
     * @param cacheKey
     * @return
     */
    Long llen(String cacheKey);

    /**
     * 获取列表类型的数据
     * @param cacheKey
     * @return
     */
    List<String> lget(String cacheKey,int start,int end);
}
