package com.springmvc.controller;

import com.springmvc.Filter.CommonFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(CommonFilter.class);

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request, Model model){
        log.info("执行了login方法！");
        System.out.println("login！");
        ModelAndView mv = new ModelAndView();
        //封装要显示到视图的数据
        mv.addObject("msg","hello myfirst mvc");
        //视图名
        mv.setViewName("login");
        return mv;
    }
}
