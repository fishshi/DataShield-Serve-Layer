package com.datashield.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.datashield.component.RedisComponent;
import com.datashield.entity.UserInfo;
import com.datashield.exception.BusinessException;
import com.datashield.mapper.UserInfoMapper;
import com.datashield.service.UserService;
import com.datashield.util.AvatarUtil;
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
        Long id = UserContextUtil.getUser().getId();
        String originalFilename = file.getOriginalFilename();
        String suffix = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String newFileName = id + suffix;

        String fullPath = "/datashield/tmp/" + newFileName;
        File dest = new File(fullPath);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new BusinessException("头像上传失败");
        }
        String avatarUrl = AvatarUtil.uploadAvatar(fullPath);
        return avatarUrl;
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Long id = UserContextUtil.getUser().getId();
        UserInfo userInfo = userInfoMapper.selectById(id);
        userInfo.setAvatarUrl(avatarUrl);
        userInfoMapper.updateById(userInfo);
    }
}
