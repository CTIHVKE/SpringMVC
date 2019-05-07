package com.springmvc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.springmvc.Filter.CommonFilter;
import com.springmvc.entity.SysUser;
import com.springmvc.service.SysUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.jackson.annotate.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class SysUserController {
    private final Logger log = LoggerFactory.getLogger(CommonFilter.class);

    @Autowired
    @Qualifier("sysUserServiceImpl")
    private SysUserService service;

//    @RequestMapping(value = "/doLogin")
//    @ResponseBody
//    public void doLogin(HttpServletRequest request, HttpServletResponse response){
//        System.out.println(model.getPassword());
//
//        System.out.println(model.getLoginname());
//    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public String doLogin(HttpServletRequest request, HttpServletResponse response) {
        log.info("doLogin！");
        System.out.println("doLogin！");
        String loginname = "error!";
        String password = "";
        String userinfo = "";
        Map<String, String> map = new HashMap<String, String>();
        String jsonString;
        try {
            String s2 = request.getParameter("loginname");
            String s3 = request.getParameter("password");
            String inpassword = DigestUtils.md5Hex(s3);
            System.out.println(inpassword + "----");
            System.out.println(s2 + "--userinfo--" + s3);
            String s1 = JSON.toJSONString(userinfo);
            System.out.println(s1);
            SysUser userEntity = service.selectByLoginname(s2);
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
            renderData(response, loginname);
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

    /**
     * 通过PrintWriter将响应数据写入response，ajax可以接受到这个数据
     *
     * @param response
     * @param data
     */
    private void renderData(HttpServletResponse response, String data) {
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.print(data);
        } catch (IOException ex) {
            log.info(SysUserController.class.getName());
        } finally {
            if (null != printWriter) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }

    @RequestMapping(value = "/setUserList",method = RequestMethod.GET)
    @ResponseBody
    public void setUserList(){
        System.out.println("控制器：setUserList");
         service.setUserList();
    }

    @RequestMapping(value = "/getUserList",method = RequestMethod.GET)
    @ResponseBody
    public String getUserList(){
        System.out.println("控制器：getUserList");
        List<SysUser> _userlist = service.getUserList();
        return JSON.toJSONString(_userlist);
    }

    @RequestMapping(value = "/setUserHash",method = RequestMethod.GET)
    @ResponseBody
    public void setUserHash(){
        System.out.println("控制器：setUserHash");
        service.setUserHash();
    }

    @RequestMapping(value = "/getUserHash",method = RequestMethod.GET)
    @ResponseBody
    public String getUserHash(){
        System.out.println("控制器：getUserHash");
        return service.getUserHash();
    }
}
