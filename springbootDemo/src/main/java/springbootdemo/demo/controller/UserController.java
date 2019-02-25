package springbootdemo.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class UserController {
    @PostMapping(value="/user/login" )
    public String login(@RequestParam String username , @RequestParam String password,
                        Map<String,Object> map, HttpSession session){
        if(!StringUtils.isEmpty(username) && "123456".equals(password)){
            session.setAttribute("username",username);
            return "redirect:/main";
        }
        map.put("msg","用户名或者密码不正确");
        return "login";

    }
}
