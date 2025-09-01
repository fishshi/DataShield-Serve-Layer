package com.datashield.exception;

/**
 * 业务异常类
 */
public class BusinessException extends RuntimeException {
    /**
     * 业务异常类构造函数
     *
     * @param message 异常提示信息
     */
    public BusinessException(String message) {
        super(message);
    }
}