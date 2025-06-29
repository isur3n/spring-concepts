package me.suren.springconcepts.app.exception;

import jakarta.servlet.ServletException;
import lombok.Getter;

@Getter
public class CustomAppException extends ServletException {

    public CustomAppException(String message) {
        super(message);
    }

    public CustomAppException(String message, int code) {
        super(message);
        this.code = code;
    }

    private Integer code = 500;
}
