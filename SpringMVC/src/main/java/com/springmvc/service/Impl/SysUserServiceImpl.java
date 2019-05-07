package com.springmvc.service.Impl;

import com.alibaba.fastjson.JSON;
import com.springmvc.base.RedisCacheStorageImpl;
import com.springmvc.base.RedisClient;
import com.springmvc.base.RedisKeyEnum;
import com.springmvc.dao.SysUserMapper;
import com.springmvc.entity.SysUser;
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

    @Override
    public List<SysUser> getUserList() {
        List<SysUser> list = new ArrayList<SysUser>();

        RedisClient redisClient = new RedisClient();
        JedisPool pool = new JedisPool();
        redisClient.setPool(pool);
        RedisCacheStorageImpl<String> redis = new RedisCacheStorageImpl<String>();
        redis.setRedisClient(redisClient);
        System.out.println("getUserList:" + RedisKeyEnum.SysUserList);
        Long len = redis.llen(RedisKeyEnum.SysUserList);
        System.out.println(RedisKeyEnum.SysUserList + ": Redis:len = " + len);
        if(len > 0){
            List<String> lists = redis.lget(RedisKeyEnum.SysUserList,0,10);
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
        System.out.println("setUserList:" + RedisKeyEnum.SysUserList);
        List<SysUser> list = new ArrayList<SysUser>();
        list = mapper.selectAll();
        System.out.println("list-size:" + list.size());
        list.forEach(user ->{
            System.out.println("list:" + JSON.toJSONString(user));
            redis.lpush(RedisKeyEnum.SysUserList, JSON.toJSONString(user));
        });
//        for (SysUser user: list) {
//            redis.lpush(ListKey, JSON.toJSONString(user));
//        }
    }

    @Override
    public void setUserHash(){
        RedisClient redisClient = new RedisClient();
        JedisPool pool = new JedisPool();
        redisClient.setPool(pool);
        RedisCacheStorageImpl<String> redis = new RedisCacheStorageImpl<String>();
        redis.setRedisClient(redisClient);
        System.out.println("setUserList:" + RedisKeyEnum.Hash);
        SysUser user = mapper.selectByLoginname("ihvke");
        System.out.println("list:" + JSON.toJSONString(user));
        redis.hset(RedisKeyEnum.Hash,"FieldUserid", JSON.toJSONString(user.getUserid()));
        redis.hset(RedisKeyEnum.Hash,"FieldUsername", JSON.toJSONString(user.getUsername()));
    }

    @Override
    public String getUserHash(){
        RedisClient redisClient = new RedisClient();
        JedisPool pool = new JedisPool();
        redisClient.setPool(pool);
        RedisCacheStorageImpl<String> redis = new RedisCacheStorageImpl<String>();
        redis.setRedisClient(redisClient);
        System.out.println("setUserList:" + RedisKeyEnum.Hash);
        SysUser user = mapper.selectByLoginname("ihvke");
        System.out.println("list:" + JSON.toJSONString(user));
        String userid = redis.hget(RedisKeyEnum.Hash,"FieldUserid");
        String username = redis.hget(RedisKeyEnum.Hash,"FieldUsername");
        return String.format("userid:{0}--username:{1}",userid,username);
    }

    @Override
    public SysUser selectByLoginname(String loginname) {

        RedisClient redisClient = new RedisClient();
        JedisPool pool = new JedisPool();
        redisClient.setPool(pool);
        RedisCacheStorageImpl<String> redis = new RedisCacheStorageImpl<String>();
        redis.setRedisClient(redisClient);
        redis.set(RedisKeyEnum.SysUserList,"啦啦啦redis成功使用："  +  loginname);

        String redislist = redis.get(RedisKeyEnum.LoginName);
        System.out.println("redislist:" + redislist);

        return mapper.selectByLoginname(loginname);
    }

    @Override
    public SysUser selectByPrimaryKey(Short key) {
        return mapper.selectByPrimaryKey(key);
    }
}
