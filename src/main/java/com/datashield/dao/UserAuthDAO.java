package com.datashield.dao;

import com.datashield.entity.UserAuth;

/**
 * 用户认证 DAO 接口
 */
public interface UserAuthDAO {
    /**
     * 根据 id 获取用户认证信息
     *
     * @param id 用户 id
     *
     * @return 用户认证信息
     */
    public UserAuth getUserAuthById(Long id);

    /**
     * 根据用户名获取用户认证信息
     *
     * @param username 用户名
     *
     * @return 用户认证信息
     */
    public UserAuth getUserAuthByUsername(String username);

    /**
     * 创建新用户认证信息
     *
     * @param user 新用户认证信息
     *
     * @return 新用户认证信息
     */
    public UserAuth insert(UserAuth user);
}
