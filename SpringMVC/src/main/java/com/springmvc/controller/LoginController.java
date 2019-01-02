package com.springmvc.controller;


import com.springmvc.Filter.CommonFilter;
import com.springmvc.Filter.RequestUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

public class LoginController {

    private final Logger log = LoggerFactory.getLogger(CommonFilter.class);

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String toHome( String test) {
        log.info("执行了Hello方法！");
        return "loginSuccess";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(HttpServletRequest request, String userName, String password){
        log.info("执行了login方法！");
        password = DigestUtils.md5Hex(password);
//        User user = userService.findUser(userName,password);
//        if(user!=null){
//            request.getSession().setAttribute("userId", user.getId());
//            request.getSession().setAttribute("user", userName);
//
//            return "redirect:" + RequestUtil.retrieveSavedRequest(request);//跳转至访问页面
//        }else{
//            log.info("用户不存在");
//            request.getSession().setAttribute("message", "用户名不存在，请重新登录");

            return "home";
//        }
    }
}
