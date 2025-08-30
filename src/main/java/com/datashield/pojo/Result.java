package com.datashield.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用返回结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    /**
     * 状态码, 200 成功, 401 为未授权, 500 为服务器错误
     */
    private int code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;
}