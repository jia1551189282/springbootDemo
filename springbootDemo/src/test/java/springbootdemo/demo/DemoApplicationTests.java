package springbootdemo.demo;

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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit4.SpringRunner;
import springbootdemo.demo.entities.Employee;
import springbootdemo.demo.mapper.EmployeeMapper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    DataSource dataSource;
    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate<Object,Employee> employeeRedisTemplate;

    @Autowired
    JavaMailSenderImpl mailSender;


    @Test
    public void contextLoads() throws SQLException {
        //org.apache.tomcat.jdbc.pool.DataSource
        System.out.println(dataSource.getClass());

        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();

    }

    @Test
    public void employee(){
        Employee employee = employeeMapper.getEmployeeById(1);
        System.out.println(employee);
    }

    @Test
    public void redisTest(){
        redisTemplate.opsForValue().set("emp-1","玊玊");
    }

    //自定义的序列化数据
    @Test
    public void myRedisTemplate(){
        Employee employee = employeeMapper.getEmployeeById(1);
        employeeRedisTemplate.opsForValue().set("emp-2",employee);
    }
    //简单邮件测试
    @Test
    public void mailTest(){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //设置邮件
        mailMessage.setSubject("通知");
        mailMessage.setText("今晚 7:20开会");

        mailMessage.setTo("zjforeverit@163.com");
        mailMessage.setFrom("1551189282@qq.com");

        mailSender.send(mailMessage);
    }

    //复杂邮件测试
    @Test
    @Scheduled(cron = "0-4 * * * * 0-6")
    public void mailTest2() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper =new MimeMessageHelper(mimeMessage,true);

        mimeMessageHelper.setSubject("开会");
        mimeMessageHelper.setText("<b style='color:red'>今天下午5:00开技术分享会</b>",true);

        mimeMessageHelper.addAttachment("1.jpg",new File("C:\\Users\\mx-chenz\\Desktop\\15bOOOPICae.jpg"));

        mimeMessageHelper.setTo("zjforeverit@163.com");
        mimeMessageHelper.setFrom("1551189282@qq.com");


        mailSender.send(mimeMessage);
    }
    @Test
    @Scheduled(cron = "0/4 * * * * 0-6")
    public void testSms() {


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




}

