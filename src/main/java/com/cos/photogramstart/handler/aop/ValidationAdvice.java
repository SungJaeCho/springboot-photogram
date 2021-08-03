package com.cos.photogramstart.handler.aop;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component // RestController, Service 모든것들이 Compnent를 상속해서 만들어져 있음.
@Aspect
public class ValidationAdvice {

    @Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("web api 컨트롤러====================");
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg: args) {
            if(arg instanceof BindingResult) { // bindingResult 타입이 있으면
                BindingResult bindingResult = (BindingResult) arg; //downcasting

                if(bindingResult.hasErrors()){
                    Map<String, String> errorMap = new HashMap<>();
                    for(FieldError error : bindingResult.getFieldErrors()){
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationApiException("유효성검사실패함", errorMap);
                }

            }
        }
        //proceedingJoinPoint -> 해당 함수에 있는 모든 정보에 접근할 수 있는 파라미터
        return proceedingJoinPoint.proceed(); //apiAdvice실행 후 접근한 함수의 실행
    }

    @Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("web 컨트롤러====================");
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg: args) {
            if(arg instanceof BindingResult) { // bindingResult 타입이 있으면
                BindingResult bindingResult = (BindingResult) arg; //downcasting

                if(bindingResult.hasErrors()){
                    Map<String, String> errorMap = new HashMap<>();
                    for(FieldError error : bindingResult.getFieldErrors()){
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성검사실패함", errorMap);
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }
}
