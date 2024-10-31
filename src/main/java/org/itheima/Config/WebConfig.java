package org.itheima.Config;


import org.itheima.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration//告诉大家这是个config 并放入ioc容器中 其中implement是个接口的意思，接口就是一大堆函数的集合，需要放进ioc容器中。
public class WebConfig implements WebMvcConfigurer {
    @Autowired//获得ioc容器中的 登录验证器
    private LoginInterceptor loginInterceptor;

    @Override//重写拦截器的函数，
    public void addInterceptors(InterceptorRegistry registry){
        //登录和注册接口不拦截
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/login","/user/register");
    }
}
