package com.cos.photogramstart.web;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@Controller // 1.Ioc 2.파일을 리턴하는 컨트롤러
@RequiredArgsConstructor // final 필드를 DI할때 사용
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService; //전역변수의 final은 무조건 생성자를 통해 초기화를 해줘야함.

    @GetMapping("/auth/signin")
    public String signinForm(){
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm(){
        return "auth/signup";
    }

    // 회원가입버튼 -> /auth/signup -> /auth/signin
    @PostMapping("/auth/signup")
    public String signup(SignupDto signupDto){
        log.info(signupDto.toString());
        // User <- SignupDto
        User user = signupDto.toEntity();
        User userEntity = authService.회원가입(user);
        System.out.println(userEntity);
        return "auth/signin";
    }
}
