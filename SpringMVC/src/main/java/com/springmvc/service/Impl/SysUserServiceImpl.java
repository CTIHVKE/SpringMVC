package com.springmvc.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springmvc.base.Impl.RedisCacheStorageImpl;
import com.springmvc.base.Impl.RedisClientImpl;
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
import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
//    @Resource
    @Autowired
    @Qualifier("sysUserMapper")
    private SysUserMapper mapper;


    @Override
    public List<SysUser> getUserList() {
        List<SysUser> list1 = new ArrayList<SysUser>();

        RedisClient redisClient = new RedisClientImpl();
        JedisPool jedisPool = new JedisPool();
        redisClient.setPool(jedisPool);
        RedisCacheStorageImpl redis = new RedisCacheStorageImpl();
        redis.setRedisClient(redisClient);
        //key
        byte[] bytes = RedisKeyEnum.SysUserList.toString().getBytes();
        String strKey = new String(bytes);
//        String encoded = Base64.getEncoder().encodeToString(bytes);
        System.out.println("1：getUserList:" + strKey);
        Long len = redis.llen(bytes);
        System.out.println("2:" + RedisKeyEnum.SysUserList + ": Redis:len = " + len);
        if(len > 0){
            List<byte[]> lists = redis.lget(bytes,0,10);
            System.out.println("3:" + new Date().toString() + "--Lists-len:" + lists.size());
            for(int i = 0;i< lists.size();i++){
                String list = new String(lists.get(i));
//                String listEncoded = Base64.getEncoder().encodeToString(lists.get(i));
                System.out.println("4:2019-user-json:" + JSONObject.toJSON(list));
                System.out.println("5:2019-user-list:\"" + list + "\"");
//                System.out.println("Base64--2019-user:" + JSONObject.toJSON(listEncoded));
//                JSONObject userJson = JSONObject.parseObject(userString);
//                JSONObject userJson = JSONObject.parseObject(lists.get(i));
//                System.out.println(userJson.toJSONString());
                SysUser user = JSONObject.parseObject(list,SysUser.class);
//                SysUser user = JSON.parseObject(list,SysUser.class);
                System.out.println("6:" + user.getLoginname());
                list1.add(user);
            }
//            lists.forEach(userStr -> {
//                System.out.println("2019-user:" + JSON.toJSON(userStr));
//                String userString = "{\"id\":1,\"name\",\"lz\"}";
//                System.out.println(userString);
//                JSONObject userJson = JSONObject.parseObject(userString);
//                User user = JSON.toJavaObject(userJson,User.class);
//                JSONObject userJson = JSONObject.parseObject(userStr);
//                System.out.println(userJson.toJSONString());
//                SysUser user = JSON.toJavaObject(userJson,SysUser.class);
//                System.out.println("999user:" + JSON.toJSONString(user));
//                list1.add(user);
//            });
        }else {
            SysUser user = mapper.selectByPrimaryKey(Short.parseShort("2"));
            list1.add(user);
            System.out.println("5:" + user.getLoginname() + "--service");
        }
        return list1;
    }

    @Override
    public void setUserList() {
        RedisClient redisClient = new RedisClientImpl();
        JedisPool jedisPool = new JedisPool();
        redisClient.setPool(jedisPool);
        RedisCacheStorageImpl redis = new RedisCacheStorageImpl();
        redis.setRedisClient(redisClient);
        //key
        byte[] bytes = RedisKeyEnum.SysUserList.toString().getBytes();
        redis.remove(bytes);
        System.out.println("setUserList:" + RedisKeyEnum.SysUserList);
        List<SysUser> list = new ArrayList<SysUser>();
        list = mapper.selectAll();
        System.out.println("list-size:" + list.size());
        list.forEach(user ->{
//            System.out.println("list:" + JSONObject.toJSON(user));
//            JSON.toJSONString（用这种方式转，会产生转义符，导至后面把序列化失败）
//            System.out.println("list:" + JSONObject.toJSON(user).toString()); //JSON.toJSONString
            redis.lpush(bytes, JSONObject.toJSON(user).toString().getBytes());
        });
//        for (SysUser user: list) {
//            redis.lpush(ListKey, JSON.toJSONString(user));
//        }
    }

    @Override
    public void setUserHash(){
        RedisClient redisClient = new RedisClientImpl();
        RedisCacheStorageImpl redis = new RedisCacheStorageImpl();
        redis.setRedisClient(redisClient);
        System.out.println("setUserList:" + RedisKeyEnum.Hash);
        SysUser user = mapper.selectByLoginname("ihvke");
        System.out.println("list:" + JSON.toJSONString(user));
        //key
        byte[] bytes = RedisKeyEnum.Hash.toString().getBytes();
        redis.hset(bytes,"FieldUserid".getBytes(), JSON.toJSONString(user.getUserid()).getBytes());
        redis.hset(bytes,"FieldUsername".getBytes(), JSON.toJSONString(user.getUsername()).getBytes());
    }

    @Override
    public String getUserHash(){
        RedisClient redisClient = new RedisClientImpl();
        RedisCacheStorageImpl redis = new RedisCacheStorageImpl();
        redis.setRedisClient(redisClient);
        System.out.println("setUserList:" + RedisKeyEnum.Hash);
        SysUser user = mapper.selectByLoginname("ihvke");
        System.out.println("user:" + JSON.toJSONString(user));
        //key
        byte[] bytes = RedisKeyEnum.Hash.toString().getBytes();
        byte[] userid = redis.hget(bytes,"FieldUserid".getBytes());
        byte[] username = redis.hget(bytes,"FieldUsername".getBytes());
        return String.format("userid:%s--username:%s",userid,username);
    }

    @Override
    public SysUser selectByLoginname(String loginname) {

        RedisClient redisClient = new RedisClientImpl();
        RedisCacheStorageImpl redis = new RedisCacheStorageImpl();
        redis.setRedisClient(redisClient);
        //key
        byte[] bytes = RedisKeyEnum.SysUserList.toString().getBytes();
        redis.set(bytes,("啦啦啦redis成功使用："  +  loginname).getBytes());

        //key
        byte[] bytes_loginname = RedisKeyEnum.LoginName.toString().getBytes();
        byte[] redislist = redis.get(bytes_loginname);
        System.out.println("redislist:" + redislist);

        return mapper.selectByLoginname(loginname);
    }

    @Override
    public SysUser selectByPrimaryKey(Short key) {
        return mapper.selectByPrimaryKey(key);
    }
}
