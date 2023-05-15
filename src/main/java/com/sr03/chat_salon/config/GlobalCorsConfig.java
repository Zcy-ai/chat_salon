package com.sr03.chat_salon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class GlobalCorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        //添加 CORS配置信息
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //是否发送 Cookie
        corsConfiguration.setAllowCredentials(true);
        //放行哪些原始域
        corsConfiguration.addAllowedOrigin("*");
        //放行哪些原始请求头部信息
        corsConfiguration.addAllowedHeader("*");
        //放行哪些请求方式
        corsConfiguration.addAllowedMethod("GET");
        corsConfiguration.addAllowedMethod("POST");
        corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("DELETE");
        corsConfiguration.addAllowedMethod("OPTIONS");
        // 添加映射路径
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 返回新的CorsFilter
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}
