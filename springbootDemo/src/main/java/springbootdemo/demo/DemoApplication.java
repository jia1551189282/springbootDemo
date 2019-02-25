package springbootdemo.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;
@MapperScan("springbootdemo.demo.mapper")
@EnableCaching
@SpringBootApplication
@EnableScheduling  //开启定时任务
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    /*添加自定义的视图解析器*/
    /*@Bean
    public ViewResolver  myViewResolver(){
        return new MyViewResolver();
    }
    public static  class  MyViewResolver implements  ViewResolver{

        @Override
        public View resolveViewName(String s, Locale locale) throws Exception {
            return null;
        }
    }*/

}

