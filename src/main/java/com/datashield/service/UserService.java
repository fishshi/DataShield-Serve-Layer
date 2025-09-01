package com.datashield.service;

/**
 * 用户服务接口
 */
public interface UserService {
    /**
     * 根据令牌中的用户 id 登出当前用户
     */
    void logout();
}
