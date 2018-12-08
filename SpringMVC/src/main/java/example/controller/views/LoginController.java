package example.controller.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import example.model.Users;

@Controller
@RequestMapping("/views")
public class LoginController {

    @RequestMapping("/login")
    public String Login(){
        return "views/login";
    }

    @RequestMapping("/show")
    public String Show(){
        return "views/show";
    }

    // 处理登录的控制器
    @RequestMapping(value = "/islogin", method = RequestMethod.POST)
    public ModelAndView checkLogin(Users users) {

        ModelAndView mav = new ModelAndView();
        if (users.getLoginname().equals("lisi")
                && users.getLoginpwd().equals("123")) {
            users.setLoginname("李四");
            users.setLoginpwd("123456");
            // 返回的数据
            mav.addObject("users", users);
            // 跳转的页面
            mav.setViewName("forward:show");
        } else {
            mav.addObject("msg", "用户名或者密码错误");
            // 跳转的页面
            mav.setViewName("forward:login");
        }
        return mav;
    }
}
