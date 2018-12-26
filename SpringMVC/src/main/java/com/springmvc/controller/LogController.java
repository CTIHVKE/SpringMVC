package com.springmvc.controller;

import com.springmvc.dao.LogMapper;
import com.springmvc.entity.Log;
import com.springmvc.service.Impl.LogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value="/")
public class LogController {
    private ApplicationContext applicationContext;
    @Autowired
    private LogMapper mapper;

    //ajax请求，返回一个对象
    @RequestMapping(value = "/log",method = RequestMethod.POST)
    public @ResponseBody Log list(String logUser,String logTitle){
        //访问数据库或处理数据的代码

//        System.out.println("logUser："+ logUser + "-logTitle：" + logTitle);
        //加载spring配置文件
        applicationContext = new
                ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        //导入要测试的
        mapper = applicationContext.getBean(LogMapper.class);

        String uuid = "079329b2-0e2e-408f-9cc7-5fda4c2188e4";
        Log log = mapper.selectByPrimaryKey(uuid);
//        String text = log.getLogUser() + "-" + log.getLogTime();
//        System.out.println(text);

        if(logUser != null){
            log.setLogUser(logUser);
        }
        if(logTitle != null){
            log.setLogTitle(logTitle);
        }
//        System.out.println("logUser："+ log.getLogUser() + "-logTitle：" + log.getLogTitle());

        return log;
    }

    @RequestMapping("/getstring")
    public @ResponseBody String GetString(String string){

        String newstr = new StringBuffer(string).reverse().toString();
        return  newstr;
    }

    @RequestMapping(value = "/post",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> Register(@RequestBody Log log, HttpServletRequest request) {
        System.out.println("post");
        Log l = new LogServiceImpl().logSelective(log);
        request.setAttribute("Log", log);
        Map<String,String> map = new HashMap<String, String>();

        map.put("code", "0");
        map.put("logUser", l.getLogUser());
        return map;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUserList() {
        List<Log> list = new ArrayList<Log>();
        Log um = new Log();
        um.setLogUser("1");
        um.setLogTitle("649");
        list.add(um);
        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("total", "1");
        modelMap.put("data", list);
        modelMap.put("success", "true");
        return modelMap;
    }
}
