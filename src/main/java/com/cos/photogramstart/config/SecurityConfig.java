package com.cos.photogramstart.config;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
//            .usernameParameter("username") // Username 파라미터 명 변경가능
//            .successHandler(new AuthenticationSuccessHandler() { //이런식으로 로그인 성공후 로직도 설정가능하다.
//                @Override
//                public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//
//                }
//            })
            .and()
            .oauth2Login() // form 로그인도 하는데 oauth2로그인도 할거야
            .userInfoEndpoint()// oauth2로그인을 하면 최종응답을 회원정보로 바로 받을 수 있다.(인증은 시큐리티에게 맡김)
            .userService(oAuth2DetailsService)
        ;
    }
}
