package springbootdemo.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.ContentVersionStrategy;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import springbootdemo.demo.conpoment.LoginHandlerInterceptor;
import springbootdemo.demo.conpoment.MyLocalResolver;

/**
 *扩展了springboot 给我们自动配置springmvc    增加了自己的配置
 * 继承 WebMvcConfigurerAdaper
 * 既保留了所有的自动配置  也扩展了我们自己的配置  不能标注@EableWebMvc  标注了就会全面接管springmvc的配置   自动配置就不起作用了
 */
@Configuration
public class MyConfig extends WebMvcConfigurerAdapter {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
      /*  super.addViewControllers(registry);*/
        //浏览器发送/success请求   跳转到success界面
        registry.addViewController("/success").setViewName("success");
    }



    /*
     *所有的webMvcConfigurerAdapter 都会一起起作用
     */
    @Bean
    public  WebMvcConfigurerAdapter webMvcConfigurerAdapter(){


        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                //super.addViewControllers(registry);
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
                registry.addViewController("/main").setViewName("dashboard");
                registry.addViewController("/main.html").setViewName("dashboard");

            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                //super.addInterceptors(registry);
                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                        .excludePathPatterns("/index.html","/user/login","/");
            }
        };
        return adapter;
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        VersionResourceResolver versionResourceResolver = new VersionResourceResolver()
                .addVersionStrategy(new ContentVersionStrategy(), "/**");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/")
                .setCachePeriod(2592000).resourceChain(true).addResolver(versionResourceResolver);
        super.addResourceHandlers(registry);
    }

    @Bean
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        return new ResourceUrlEncodingFilter();
    }
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocalResolver();
    }


}
