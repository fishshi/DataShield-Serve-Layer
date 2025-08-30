package com.datashield.dao;

import com.datashield.entity.UserInfo;

/**
 * 用户信息 DAO 接口
 */
public interface UserInfoDAO {
    /**
     * 根据 id 获取用户信息
     *
     * @param id 用户 id
     *
     * @return 用户信息
     */
    public UserInfo getUserInfoById(Long id);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     *
     * @return 用户信息
     */
    public UserInfo getUserInfoByUsername(String username);

    /**
     * 创建新用户信息
     *
     * @param userInfo 新用户信息
     */
    public UserInfo insert(UserInfo userInfo);

    /**
     * 根据 id 更新用户信息
     *
     * @param userInfo 需要更新的用户信息
     */
    public UserInfo updateById(UserInfo userInfo);
}
