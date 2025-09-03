package com.datashield.controller.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 修改密码请求参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordReq {
    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;
}
