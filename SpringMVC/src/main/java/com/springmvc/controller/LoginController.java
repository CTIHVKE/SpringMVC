package com.springmvc.controller;


import com.springmvc.Filter.CommonFilter;
import com.springmvc.Filter.RequestUtil;
import com.springmvc.entity.SysUser;
import com.springmvc.service.Impl.UserServiceImpl;
import com.springmvc.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.JsonException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(CommonFilter.class);
    @Autowired
    @Qualifier("userServiceImpl")
    private UserServiceImpl userService;
//    @RequestMapping(value = "/Home",method = RequestMethod.GET)
//    public ModelAndView toHome() {
//        log.info("执行了Hello方法！");
//        System.out.println("执行了Hello方法！");
//        ModelAndView mv = new ModelAndView();
//        //封装要显示到视图的数据
//        mv.addObject("msg","hello myfirst mvc");
//        //视图名
//        mv.setViewName("/WEB-INF/Home.jsp");
//        return mv;
//    }

    //登录
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpServletRequest request,@RequestBody SysUser user) {
        log.info("执行了login方法！");
        System.out.println("执行了login方法！");
        try {
            System.out.println(user.getLoginname());
//            System.out.println(DigestUtils.md5Hex("1"));
//            System.out.println(DigestUtils.md5Hex("xtesefcu2"));
//            System.out.println(DigestUtils.md5Hex("ihvke"));
            System.out.println(user.getPassword());
//            userService = new UserServiceImpl();
            String password = DigestUtils.md5Hex(user.getPassword());
            System.out.println("password:" + password);
            System.out.println("redirect:" + RequestUtil.retrieveSavedRequest(request));
//            SysUser userEntity = userService.SelectByLoginName(user);
            user.setUserid(Short.parseShort("2"));
            System.out.println(user.getUserid() + "--id");
            SysUser userEntity = userService.SelectByKey(user);
            if (userEntity != null) {
                String loginname = userEntity.getLoginname();
                System.out.println(loginname + "--entity");
                request.getSession().setAttribute("userId", userEntity.getUserid());
                request.getSession().setAttribute("loginname", loginname);
                if (password == userEntity.getPassword()) {
                    userEntity.setLasttime(new Date());
//                    userService.
//                    request.getSession().setAttribute("ihvke", loginname);
//                    request.getSession().setAttribute("password", password);
                    return "redirect:/Home";//跳转至访问页面
//            return "redirect:" + RequestUtil.retrieveSavedRequest(request);//跳转至访问页面
                } else {
                    request.getSession().setAttribute("message", "密码错误，请重新登录！");
                    return "redirect:login";//跳转至登录页面
                }
            } else {
                log.info("用户不存在");
                request.getSession().setAttribute("message", "用户名不存在，请重新登录");
                return "redirect:login";//跳转至登录页面
//                return "redirect:" + RequestUtil.retrieveSavedRequest(request);//跳转至访问页面
            }
        } catch (JsonException e) {
            e.printStackTrace();
        }
        request.getSession().setAttribute("message", "登录错误！");
        return "redirect:login";
    }

    //注册
    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    public String register(HttpServletRequest request, SysUser user) {
        log.info("执行了register方法！");
        System.out.println("执行了register方法！");
        try {
//            userService = new UserServiceImpl();
            String password = DigestUtils.md5Hex(user.getPassword());
            System.out.println("redirect:" + RequestUtil.retrieveSavedRequest(request));
            SysUser userEntity = userService.SelectByLoginName(user);
            if (userEntity != null) {
                String loginname = userEntity.getLoginname();
                request.getSession().setAttribute("message",  "用户名【"+ loginname + "】已存在！");
                return "redirect:/login";//跳转至访问页面
            } else {
                userEntity.setUsername(user.getUsername());
                userEntity.setUserguid(UUID.randomUUID().toString());
                userEntity.setLoginname(user.getLoginname());
                userEntity.setPassword(password);
                userEntity.setCreatedate(new Date());
                userEntity.setModifydate(new Date());
                userService.insert(userEntity);
                request.getSession().setAttribute("message", "用户注册成功！");
                return "redirect:login";//跳转至登录页面
            }
        } catch (JsonException e) {
            e.printStackTrace();
        }
        request.getSession().setAttribute("message", "注册错误！");
        return "redirect:register";
    }
}
