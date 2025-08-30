package com.datashield.service;

/**
 * 用户认证服务接口
 */
public interface AuthService {
    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     *
     * @return JWT token, null: 登录失败
     */
    public String login(String username, String password);

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     *
     * @return JWT token
     */
    public String register(String username, String password);

    /**
     * 检查用户名是否重复
     *
     * @param username 用户名
     *
     * @return true: 可以注册, false: 已存在
     */
    public Boolean canRegister(String username);
}
