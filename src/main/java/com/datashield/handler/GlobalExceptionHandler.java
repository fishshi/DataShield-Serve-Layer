package com.datashield.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.datashield.exception.BusinessException;
import com.datashield.pojo.Result;
import com.datashield.util.ResultUtil;

/**
 * 全局异常处理器
 */
@RestControllerAdvice(annotations = { RestController.class })
public class GlobalExceptionHandler {
    /**
     * 业务异常处理
     *
     * @param e 业务异常{@link BusinessException}
     *
     * @return 返回错误结果
     */
    @ExceptionHandler(value = BusinessException.class)
    public Result<String> businessException(BusinessException e) {
        return ResultUtil.error(e.getMessage());
    }

    /**
     * 其他异常处理
     *
     * @param e 其他异常
     *
     * @return 返回错误结果
     */
    @ExceptionHandler(value = Exception.class)
    public Result<String> exception(Exception e) {
        return ResultUtil.error("未知的系统异常，请联系管理员");
    }
}
