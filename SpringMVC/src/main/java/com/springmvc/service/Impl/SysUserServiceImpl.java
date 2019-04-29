package com.springmvc.service.Impl;

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

    @Override
    public List<SysUser> getUserList() {
        List<SysUser> list = new ArrayList<SysUser>();
        SysUser user = mapper.selectByPrimaryKey(Short.parseShort("2"));
        list.add(user);
        System.out.println(user.getLoginname()+"--service");
        return list;
    }

    @Override
    public SysUser selectByLoginname(String loginname) {

        RedisClient redisClient = new RedisClient();
        JedisPool pool = new JedisPool();
        redisClient.setPool(pool);
        RedisCacheStorageImpl<String> redis = new RedisCacheStorageImpl<String>();
        redis.setRedisClient(redisClient);
        redis.set("springmvc","啦啦啦redis成功使用："  +  loginname);

        String redislist = redis.get("springmvc");
        System.out.println("redislist:" + redislist);

        return mapper.selectByLoginname(loginname);
    }

    @Override
    public SysUser selectByPrimaryKey(Short key) {
        return mapper.selectByPrimaryKey(key);
    }
}
