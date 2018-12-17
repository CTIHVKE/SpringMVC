package com.springmvc.controller;

import com.springmvc.dao.LogMapper;
import com.springmvc.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/springmvc")
public class HomeController {
    private ApplicationContext applicationContext;
    @Autowired
    private LogMapper mapper;

    @RequestMapping("/Home")
    public String reportPost(@RequestParam("begin") String begin,@RequestParam("end") String end, Model model){

        //加载spring配置文件
        applicationContext = new
                ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        //导入要测试的
        mapper = applicationContext.getBean(LogMapper.class);

        String uuid = "079329b2-0e2e-408f-9cc7-5fda4c2188e4";
        Log log = mapper.selectByPrimaryKey(uuid);
        String text = log.getLogUser() + "-" + log.getLogTime();
        System.out.println(text);

        model.addAttribute("begin",begin);
        model.addAttribute("end",end);
        model.addAttribute("formType","POSY");
        model.addAttribute("context",text);
        return  "Home";
    }

    @RequestMapping("/detail/data={name}")
    public  String reprotGet(@PathVariable("name") String data, Model model){

        model.addAttribute("data",data);
        model.addAttribute("formType","GET");
        model.addAttribute("context","reprotGet");
        return  "Home";
    }

}
