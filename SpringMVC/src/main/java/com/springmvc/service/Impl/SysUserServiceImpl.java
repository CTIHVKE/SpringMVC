package com.springmvc.service.Impl;

import com.alibaba.fastjson.JSON;
import com.springmvc.base.RedisCacheStorageImpl;
import com.springmvc.base.RedisClient;
import com.springmvc.dao.SysUserMapper;
import com.springmvc.entity.SysUser;
import com.springmvc.base.RedisCacheStorage;
import com.springmvc.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
//    @Resource
    @Autowired
    @Qualifier("sysUserMapper")
    private SysUserMapper mapper;

    final String LoginnameKey = "springmvc";
    final String ListKey = "userListkey";

    @Override
    public List<SysUser> getUserList() {
        List<SysUser> list = new ArrayList<SysUser>();

        RedisClient redisClient = new RedisClient();
        JedisPool pool = new JedisPool();
        redisClient.setPool(pool);
        RedisCacheStorageImpl<String> redis = new RedisCacheStorageImpl<String>();
        redis.setRedisClient(redisClient);
        System.out.println("getUserList:" + ListKey);
        Long len = redis.llen(ListKey);
        System.out.println(ListKey + ": Redis:len = " + len);
        if(len > 0){
            List<String> lists = redis.lget(ListKey,0,10);
            System.out.println("lists-len:" + lists.size());
            lists.forEach(userStr -> {
                System.out.println("user:" + JSON.toJSON(userStr));

                list.add((SysUser)JSON.toJSON(userStr));
            });
        }else {
            SysUser user = mapper.selectByPrimaryKey(Short.parseShort("2"));
            list.add(user);
            System.out.println(user.getLoginname() + "--service");
        }
        return list;
    }

    @Override
    public void setUserList() {
        RedisClient redisClient = new RedisClient();
        JedisPool pool = new JedisPool();
        redisClient.setPool(pool);
        RedisCacheStorageImpl<String> redis = new RedisCacheStorageImpl<String>();
        redis.setRedisClient(redisClient);
        System.out.println("setUserList:" + ListKey);
        List<SysUser> list = new ArrayList<SysUser>();
        list = mapper.selectAll();
        System.out.println("list-size:" + list.size());
        list.forEach(user ->{
            System.out.println("list:" + JSON.toJSONString(user));
            redis.lpush(ListKey, JSON.toJSONString(user));
        });
//        for (SysUser user: list) {
//            redis.lpush(ListKey, JSON.toJSONString(user));
//        }
    }

    @Override
    public SysUser selectByLoginname(String loginname) {

        RedisClient redisClient = new RedisClient();
        JedisPool pool = new JedisPool();
        redisClient.setPool(pool);
        RedisCacheStorageImpl<String> redis = new RedisCacheStorageImpl<String>();
        redis.setRedisClient(redisClient);
        redis.set(LoginnameKey,"啦啦啦redis成功使用："  +  loginname);

        String redislist = redis.get("springmvc");
        System.out.println("redislist:" + redislist);

        return mapper.selectByLoginname(loginname);
    }

    @Override
    public SysUser selectByPrimaryKey(Short key) {
        return mapper.selectByPrimaryKey(key);
    }
}
