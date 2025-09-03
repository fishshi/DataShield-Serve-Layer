package com.datashield.service;

import com.datashield.entity.UserAuth;

/**
 * 用户认证服务接口
 */
public interface AuthService {
    /**
     * 登录
     *
     * @param userAuth 用户认证信息
     *
     * @return JWT token, null: 登录失败
     */
    String login(UserAuth userAuth);

    /**
     * 注册
     *
     * @param userAuth 用户认证信息
     *
     * @return JWT token
     */
    String register(UserAuth userAuth);

    /**
     * 检查用户名是否重复
     *
     * @param username 用户名
     *
     * @return true: 可以注册, false: 已存在
     */
    Boolean canRegister(String username);

    /**
     * 更新密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     *
     * @return JWT token
     */
    String updatePassword(String oldPassword, String newPassword);
}
