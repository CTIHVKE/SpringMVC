package com.springmvc.service;

import com.springmvc.entity.SysUser;

import java.util.List;

public interface SysUserService {

    List<SysUser> getUserList();

    void setUserList();

    SysUser selectByLoginname(String loginname);

    SysUser selectByPrimaryKey(Short key);
}
