package com.sky.app.library.utils.http;

/**
 * 自定义异常处理
 */
public class DefinedException extends RuntimeException {
    public DefinedException(String dessage) {
        super(dessage);
    }
}

