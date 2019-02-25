package springbootdemo.demo.service;

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
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    JavaMailSenderImpl mailSender;

    @Scheduled(cron = "0/20 * * * * 0-6")
    public void schedule() throws MessagingException {
        //System.out.println("===============hello============schedule");

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper =new MimeMessageHelper(mimeMessage,true);

        mimeMessageHelper.setSubject("开会");
        mimeMessageHelper.setText("<b style='color:red'>今天下午5:00开技术分享会</b>",true);

        mimeMessageHelper.addAttachment("1.jpg",new File("C:\\Users\\mx-chenz\\Desktop\\15bOOOPICae.jpg"));

        mimeMessageHelper.setTo("zjforeverit@163.com");
        mimeMessageHelper.setFrom("1551189282@qq.com");


        mailSender.send(mimeMessage);


    }
    @Scheduled(cron = "0/30 * * * * 0-6")
    public void scheduleMessage(){

        CloseableHttpClient httpClient = HttpClients.createDefault();

        String entityStr = null;
        CloseableHttpResponse response = null;

        try {

            // 创建POST请求对象
            HttpPost httpPost = new HttpPost("https://open.ucpaas.com/ol/sms/sendsms");

            /*
             * 添加请求参数
             */
            // 创建请求参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            BasicNameValuePair basicNameValuePair1 = new BasicNameValuePair("sid", "9c398453fecee3d6e6170e59590f5c2e");
            BasicNameValuePair basicNameValuePair2 = new BasicNameValuePair("token", "426931e73c763abb798b6629107d1457");
            BasicNameValuePair basicNameValuePair3 = new BasicNameValuePair("appid", "e6d726c4100c4640b559bfb082ee971a");
            BasicNameValuePair basicNameValuePair4 = new BasicNameValuePair("templateid", "427939");
            BasicNameValuePair basicNameValuePair5 = new BasicNameValuePair("mobile", "13072302070");
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


