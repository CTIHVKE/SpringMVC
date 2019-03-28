package com.springmvc.service.Impl;

import com.springmvc.dao.SysUserMapper;
import com.springmvc.entity.SysUser;
import com.springmvc.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        return mapper.selectByLoginname(loginname);
    }
}
