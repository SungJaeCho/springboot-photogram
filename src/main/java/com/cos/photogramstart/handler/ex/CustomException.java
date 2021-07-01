package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomException extends RuntimeException{
    //객체를 구분할 때 사용 직렬화/역직렬화 UID
    private static final long serialVersionUID = -807520916259076805L;

    public CustomException(String message) {
        super(message); //부모에게 던짐
    }

}
