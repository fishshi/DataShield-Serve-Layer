package com.datashield.service;

import org.springframework.web.multipart.MultipartFile;

import com.datashield.entity.UserInfo;

/**
 * 用户服务接口
 */
public interface UserService {
    /**
     * 根据令牌中的用户 id 登出当前用户
     */
    void logout();

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    UserInfo getUserInfo();

    /**
     * 更新用户信息
     *
     * @param userInfo 需要更新的用户信息
     */
    void updateUserInfo(UserInfo userInfo);

    /**
     * 上传用户头像
     *
     * @param file 头像文件
     *
     * @return 头像 URL, null 为失败
     */
    String uploadAvatar(MultipartFile file);

    /**
     * 更新用户头像
     *
     * @param avatarUrl 用户头像 URL
     */
    void updateAvatar(String avatarUrl);
}
