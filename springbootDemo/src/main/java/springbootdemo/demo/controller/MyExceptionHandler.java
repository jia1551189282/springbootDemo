package springbootdemo.demo.controller;

import exception.UserNotExistException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(UserNotExistException.class)
    public String handleException(Exception e, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        //传入我们自己的错误代码  否则就不会进入到定制的错误页面的解析流程  4xx 5xx
        request.setAttribute("",500);
        map.put("code","user.notexist");
        map.put("message","用户出错了");

        request.setAttribute("ext",map);

        return "forward:/error";

    }
}
