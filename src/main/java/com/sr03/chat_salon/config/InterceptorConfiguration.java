package com.sr03.chat_salon.config;

import com.sr03.chat_salon.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //用于定义配置类，可替换 xml 文件；定义一个拦截器，相当于之前的mvc 里的配置
public class InterceptorConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //定义需要拦截的路径
        String[] addPathPatterns = {
                "/**",
        };
        //定义不需要拦截的路径
        String[] excludePathPatterns = {
                "/",
                "/login",
                "/register"
        };
        registry.addInterceptor(new UserInterceptor()) //添加要注册的拦截器对象
                .addPathPatterns(addPathPatterns) //添加需要拦截的路径
                .excludePathPatterns(excludePathPatterns); //添加不需要拦截的路径
    }
}
