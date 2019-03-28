package com.springmvc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.springmvc.Filter.CommonFilter;
import com.springmvc.entity.SysUser;
import com.springmvc.service.SysUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class SysUserController {
    private final Logger log = LoggerFactory.getLogger(CommonFilter.class);

    @Autowired
    @Qualifier("sysUserServiceImpl")
    private SysUserService service;

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public String doLogin(HttpServletRequest request) {
        log.info("doLogin！");
        System.out.println("doLogin！");
        String loginname = "error!";
        String password = "";
        String userinfo = "";
        String inpassword = DigestUtils.md5Hex("1");
        Map<String, String> map = new HashMap<String, String>();
        String jsonString;
        try {
            userinfo = request.getParameter("userinfo");
            System.out.println(userinfo + "--userinfo-");
            SysUser userEntity = service.selectByLoginname("admin");
            if (userEntity != null) {
                loginname = userEntity.getLoginname();
                password = userEntity.getPassword();
                String isTruePassword = "否";
                if(password.equals(inpassword)){
                    isTruePassword  = "是";
                }
                System.out.println("密码是否一致：" +  isTruePassword);

                System.out.println(loginname + "--oracle data--" + password);
            }
        } catch (Exception e) {
            //非业务异常处理逻辑
            System.out.println(e.getMessage());
            map.put("error", e.getMessage());
            map.put("mst", "失败！");
            jsonString = JSON.toJSONString(map, SerializerFeature.PrettyFormat);
            return jsonString;
        }
        map.put("error", "0");
        map.put("msg", "成功！");
        map.put("loginname", loginname);
        map.put("password", password);
        jsonString = JSON.toJSONString(map, SerializerFeature.PrettyFormat);
        return jsonString;
    }
}
