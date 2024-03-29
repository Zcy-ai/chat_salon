package com.sr03.chat_salon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf()
            .disable()
            .exceptionHandling()
            .authenticationEntryPoint(unauthorizedHandler)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/chat/**").permitAll() // Autoriser les requêtes WebSocket
            .antMatchers("/contact/**").permitAll()
            .antMatchers("/chat_history/**").permitAll()
            .antMatchers("/login").permitAll()
            .antMatchers("/register").permitAll()
//            .antMatchers("/deleteUser").hasAnyRole("ADMIN")
            .antMatchers("/create_chatroom/**").permitAll()
            .antMatchers("/delete_chatroom/**").permitAll()
            .antMatchers("/css/**", "/fonts/**", "/images/**", "/js/**", "/error", "/webjars/**").permitAll()
            .antMatchers("/resetPwd").permitAll()
//            .antMatchers("/admin*").hasAnyRole("ADMIN")
            .antMatchers("/chatRoom").permitAll()
            .antMatchers("/").permitAll()
            .antMatchers("/admin/**").permitAll()
            .antMatchers("/favicon.ico").permitAll()
            .anyRequest().authenticated();
    }

}

