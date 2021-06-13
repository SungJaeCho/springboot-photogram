package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity // 해당 파일로 시큐리티를 활성화
@Configuration // Ioc
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http); super삭제시 기본 security기능 비활성화
        http.csrf().disable(); //csrf토큰 비활성화
        http.authorizeRequests()
        .antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**").authenticated() //이주소는 인증필요
        .anyRequest().permitAll()
        .and()
        .formLogin()
        .loginPage("/auth/signin")
        .defaultSuccessUrl("/")
        ;
    }
}
