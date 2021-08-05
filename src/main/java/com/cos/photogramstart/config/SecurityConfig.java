package com.cos.photogramstart.config;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity // 해당 파일로 시큐리티를 활성화
@Configuration // Ioc
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2DetailsService oAuth2DetailsService;

    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http); super삭제시 기본 security기능 비활성화
        http.csrf().disable(); //csrf토큰 비활성화
        http.authorizeRequests()
            .antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**", "/api/**").authenticated() //이주소는 인증필요
            .anyRequest().permitAll()
            .and()
            .formLogin()
            .loginPage("/auth/signin") // GET
            .loginProcessingUrl("/auth/signin") // POST -> 스프링 시큐리티가 로그인 프로세스 진행
            .defaultSuccessUrl("/")
            .and()
            .oauth2Login() // form 로그인도 하는데 oauth2로그인도 할거야
            .userInfoEndpoint()// oauth2로그인을 하면 최종응답을 회원정보로 바로 받을 수 있다.(인증은 시큐리티에게 맡김)
            .userService(oAuth2DetailsService)
        ;
    }
}
