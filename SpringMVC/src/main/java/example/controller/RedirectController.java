package example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class RedirectController {
    //无参重定向
    @RequestMapping(value="/Redirect",method= RequestMethod.GET)
    public String testRedirect(){

        return "redirect:/index";
    }
    //有参重定向
//    @RequestMapping(value="/Redirect",method=RequestMethod.GET)
//    public String testRedirect(RedirectAttributes attr){
//        attr.addAttribute("a", "a");
//        attr.addFlashAttribute("b", "b");
//        return "redirect:/index";
//    }
}
