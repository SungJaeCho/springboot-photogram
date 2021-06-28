package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomApiException extends RuntimeException{
    //객체를 구분할 때 사용 직렬화/역직렬화 UID
    private static final long serialVersionUID = -807520916259076805L;

    public CustomApiException(String message) {
        super(message);
    }

    public CustomApiException(String message, Map<String, String> errorMap) {
        super(message); //부모에게 던짐
    }

}
