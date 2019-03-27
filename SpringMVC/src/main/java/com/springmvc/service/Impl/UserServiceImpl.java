package com.springmvc.service.Impl;

import com.springmvc.dao.SysUserMapper;
import com.springmvc.entity.SysUser;
import com.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT)
//表示数据库隔离级别为如果当前有就使用当前，如果没有就创建新的事务，
//隔离级别为：读已提交，也就是数据在写入的时候是无法被读的，只有提交后才能让其他事务读取，防止数据库发生脏读
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private SysUserMapper mapper;//用AutoWired注入DB层

    @Override
    public int insert(SysUser user) {
        return mapper.insert(user);
    }

    @Transactional(readOnly=true) //数据库的读取方式为：只读
    @Override
    public SysUser SelectByKey(SysUser user) {
        return mapper.selectByPrimaryKey(user.getUserid());
    }

    @Transactional(readOnly=true) //数据库的读取方式为：只读
    @Override
    public SysUser SelectByLoginName(SysUser user) {
        System.out.println(user.getLoginname() + "--Service");
//        return mapper.selectByLoginName(user.getLoginname());
        return mapper.selectByPrimaryKey(user.getUserid());
    }

    @Override
    public void UpdateByKey(SysUser user) {
        mapper.updateByPrimaryKey(user);
    }
}
