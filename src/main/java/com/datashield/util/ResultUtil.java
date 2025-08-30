package com.datashield.util;

import com.datashield.pojo.Result;

/**
 * 返回结果工具类
 */
public class ResultUtil {
    /**
     * 成功返回结果
     *
     * @param <T>  返回数据类型
     * @param data 返回数据
     *
     * @return 成功结果 {@link Result}
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(200, null, data);
    }

    /**
     * 成功返回结果
     *
     * @param <T>  返回数据类型
     * @param data 返回数据
     *
     * @return 成功结果 {@link Result}
     */
    public static <T> Result<T> success() {
        return new Result<T>(200, null, null);
    }

    /**
     * 错误返回结果
     *
     * @param <T>     返回数据类型
     * @param message 错误信息
     *
     * @return 错误结果 {@link Result}
     */
    public static <T> Result<T> error(String message) {
        return new Result<T>(500, message, null);
    }

    /**
     * 未授权返回结果
     *
     * @param <T> 返回数据类型
     *
     * @return 未授权结果 {@link Result}
     */
    public static <T> Result<T> unauthorized() {
        return new Result<T>(401, "登录失效, 请重新登录", null);
    }
}
