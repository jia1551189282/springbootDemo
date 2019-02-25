package springbootdemo.demo.controller;

import exception.UserNotExistException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springbootdemo.demo.entities.Department;
import springbootdemo.demo.entities.Employee;
import springbootdemo.demo.service.DepartmentService;
import springbootdemo.demo.service.EmployeeService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HelloController {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    DepartmentService departmentService;

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(@RequestParam("user") String user) {
        if (user.equals("aaa")) {
            throw new UserNotExistException();
        }
        return "Hello World";
    }


    @RequestMapping("/success1")
    public String thymeleafDemo(Map<String,String> map){
        map.put("hello","hello,thymeleaf");
        return "success";
    }

    @ResponseBody
    @GetMapping("/query")
    public Map<String,Object> map(){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from department");
        return maps.get(0);
    }
    @ResponseBody
    @RequestMapping("/employee/{id}")
    public Employee getEmployeeById(@PathVariable("id") Integer id){
        Employee employee = employeeService.getEmployeeById(id);
        return employee;
    }

    @ResponseBody
    @RequestMapping("/department/{id}")
    public Department getDepartmentById(@PathVariable("id")Integer id){
       Department department = departmentService.getDepartmentById(id);
       return  department;
    }

    @RequestMapping("/testSms")
    public void testSms() throws UnsupportedEncodingException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String entityStr = null;
        CloseableHttpResponse response = null;

        try {

            // 创建POST请求对象
            HttpPost httpPost = new HttpPost("http://api.feige.ee/SmsService/Send");

            /*
             * 添加请求参数
             */
            // 创建请求参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            BasicNameValuePair basicNameValuePair1 = new BasicNameValuePair("Account", "18789190917");
            BasicNameValuePair basicNameValuePair2 = new BasicNameValuePair("Pwd", "e64d6b239d5361fdcaddf278e");
            BasicNameValuePair basicNameValuePair3 = new BasicNameValuePair("Content", "测试呀呀呀");
            BasicNameValuePair basicNameValuePair4 = new BasicNameValuePair("Mobile", "13072302070");
            BasicNameValuePair basicNameValuePair5 = new BasicNameValuePair("SignId", "88549");
            list.add(basicNameValuePair1);
            list.add(basicNameValuePair2);
            list.add(basicNameValuePair3);
            list.add(basicNameValuePair4);
            list.add(basicNameValuePair5);
            // 使用URL实体转换工具
            UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");
            httpPost.setEntity(entityParam);




            // 执行请求
            response = httpClient.execute(httpPost);
            // 获得响应的实体对象
            HttpEntity entity = response.getEntity();
            // 使用Apache提供的工具类进行转换成字符串
            entityStr = EntityUtils.toString(entity, "UTF-8");

            // System.out.println(Arrays.toString(response.getAllHeaders()));

        } catch (ClientProtocolException e) {
            System.err.println("Http协议出现问题");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("解析错误");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO异常");
            e.printStackTrace();
        } finally {
            // 释放连接
            if (null != response) {
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    System.err.println("释放连接出错");
                    e.printStackTrace();
                }
            }
        }

        // 打印响应内容
        System.out.println(entityStr);

    }
    @ResponseBody
    @RequestMapping("/test")
    public String test(){
        return "jiajia";
    }
}
