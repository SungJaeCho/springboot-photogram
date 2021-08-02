package com.cos.photogramstart.handler.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component // RestController, Service 모든것들이 Compnent를 상속해서 만들어져 있음.
@Aspect
public class ValidationAdvice {

    @Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("web api 컨트롤러====================");

        //proceedingJoinPoint -> 해당 함수에 있는 모든 정보에 접근할 수 있는 파라미터
        return proceedingJoinPoint.proceed(); //apiAdvice실행 후 접근한 함수의 실행
    }

    @Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("web 컨트롤러====================");
        return proceedingJoinPoint.proceed();
    }
}
