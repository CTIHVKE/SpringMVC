package com.springmvc.util;

import com.springmvc.entity.Log;
import com.springmvc.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Encryption {
    @Autowired
    private LogService logService;

    @RequestMapping(value = "/user/register",method = RequestMethod.GET)
    public String register(){
        return "register";
    }

    public String addLog(@ModelAttribute Log log, Model model){
        Log log1 = new Log();
        log1.setLogUser(log.getLogUser());


        return "register";
    }

}
