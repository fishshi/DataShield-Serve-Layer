package com.datashield.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.datashield.component.RedisComponent;
import com.datashield.entity.UserInfo;
import com.datashield.mapper.UserInfoMapper;
import com.datashield.service.UserService;
import com.datashield.util.UserContextUtil;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RedisComponent redisComponent;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public void logout() {
        Long id = UserContextUtil.getUser().getId();
        redisComponent.delete(id.toString());
    }

    @Override
    public UserInfo getUserInfo() {
        Long id = UserContextUtil.getUser().getId();
        UserInfo userInfo = userInfoMapper.selectById(id);
        return userInfo;
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        Long id = UserContextUtil.getUser().getId();
        UserInfo oldUserInfo = userInfoMapper.selectById(id);
        userInfo.setUsername(oldUserInfo.getUsername());
        userInfo.setId(id);
        userInfo.setAvatarUrl(oldUserInfo.getAvatarUrl());
        userInfoMapper.updateById(userInfo);
    }

    @Override
    public String uploadAvatar(MultipartFile file) {
        return null;
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Long id = UserContextUtil.getUser().getId();
        UserInfo userInfo = userInfoMapper.selectById(id);
        userInfo.setAvatarUrl(avatarUrl);
        userInfoMapper.updateById(userInfo);
    }
}
