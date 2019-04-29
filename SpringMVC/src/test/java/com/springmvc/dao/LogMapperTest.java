package com.springmvc.dao;

import com.springmvc.entity.SysUser;
import com.springmvc.service.SysUserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class LogMapperTest {

    private ApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
        //加载spring配置文件
        applicationContext = new
                ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        //导入要测试的
//        userService = applicationContext.getBean(SysUserServiceImpl.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws  Exception{
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        String NowTime = df.format(new Date());
//        UUID guid =  java.util.UUID.randomUUID();
//        Log log = new Log(guid.toString(),NowTime,"IGDN","LOGTITLE-2018-12-16","LOGCONTENT-1234567890");
//        int result =  mapper.insert(log);
//        System.out.println(result);
//        assert(result == 1);
        String uuid = "079329b2-0e2e-408f-9cc7-5fda4c2188e4";
//        Log log = mapper.selectByPrimaryKey(uuid);
//        System.out.println(log.getLogUser() + "-" + log.getLogTime());

        SysUserMapper mapper = applicationContext.getBean(SysUserMapper.class);
        SysUser user = mapper.selectByPrimaryKey(Short.parseShort("2"));
        System.out.println(user.getLoginname());

        SysUserService sysUserService = applicationContext.getBean(SysUserService.class);
        List<SysUser> list = sysUserService.getUserList();
        System.out.println(list.get(0).getLoginname()+"--test");
        list.forEach(e->System.out.println(e.getLoginname()+"test3"));
    }
}