package com.springmvc.base;

import java.util.List;
import java.util.Map;

/**
* 缓存存储接口
* @param <K> key
* @param<V> value
* */
public interface RedisCacheStorage<C, K, V> {
    boolean set(C key,V value);

    /**
    * @param exp 过期时间 s
    * */
    boolean set(C key,V value, int exp);

    V get(C key);

    boolean remove(C key);

    /**
    * 设置哈希类型数据到redis 数据库
    * @param cacheKey 可以看做一张表
    * @param key   表字段
    * @param value
    * @return
    */
    boolean hset(C cacheKey,K key,V value);

    /**
     * 获取哈希表数据类型的值
     * @param cacheKey
     * @param key
     * @return
     */
    V hget(C cacheKey,K key);

    /**
     * 获取哈希类型的数据
     * @param cacheKey
     * @return
     */
    Map<K,V> hget(C cacheKey);


    /**
     * 设置列表类型数据到redis 数据库
     * @param cacheKey 可以看做一张表
     * @param value
     * @return
     */
    boolean lpush(C cacheKey,V value);

    /**
     * 获取列表数据类型的值
     * @param cacheKey
     * @return
     */
    Long llen(C cacheKey);

    /**
     * 获取列表类型的数据
     * @param cacheKey
     * @return
     */
    List<K> lget(C cacheKey,int start,int end);
}
