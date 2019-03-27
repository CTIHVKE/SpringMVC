package com.springmvc.service;

import com.springmvc.entity.SysUser;

public interface UserService {
    int insert(SysUser user);

    SysUser SelectByKey(SysUser user);

    SysUser SelectByLoginName(SysUser user);

    void UpdateByKey(SysUser user);
}
