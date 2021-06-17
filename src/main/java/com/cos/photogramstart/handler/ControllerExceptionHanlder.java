package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ControllerAdvice // 컨트롤러 실행되는거 다 낚아챔
public class ControllerExceptionHanlder {

//    @ExceptionHandler(CustomValidationException.class)
//    public CMRespDto<?> validationException(CustomValidationException e){
//        return new CMRespDto(-1, e.getMessage(), e.getErrorMap());
//    }

    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e){
        // CMRespDto, Script비교
        // 1. 클라이언트에게 응답할때는 Script가 좋음
        // 2. Ajax통신 - CMRespDto 개발자응답
        // 3. Android통신 - CMRespDto 개발자응답
        return Script.back(e.getErrorMap().toString());
    }
}
