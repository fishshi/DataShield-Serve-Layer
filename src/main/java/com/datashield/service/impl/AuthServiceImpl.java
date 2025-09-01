package com.datashield.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.datashield.component.RedisComponent;
import com.datashield.entity.UserAuth;
import com.datashield.entity.UserInfo;
import com.datashield.exception.BusinessException;
import com.datashield.mapper.UserAuthMapper;
import com.datashield.mapper.UserInfoMapper;
import com.datashield.service.AuthService;
import com.datashield.util.JwtUtil;

/**
 * 用户认证服务实现类
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserAuthMapper userAuthMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RedisComponent redisComponent;

    @Override
    public String login(UserAuth userAuth) {
        UserAuth user = userAuthMapper.selectOne(new QueryWrapper<UserAuth>().eq("username", userAuth.getUsername()));
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!user.getPassword().equals(userAuth.getPassword())) {
            return null;
        }
        String token = JwtUtil.generateToken(user.getId().toString());
        redisComponent.set(user.getId().toString(), token, 1, TimeUnit.DAYS);
        return token;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String register(UserAuth userAuth) {
        if (userAuthMapper.insert(userAuth) == 0) {
            throw new BusinessException("注册失败");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userAuth.getId());
        userInfo.setUsername(userAuth.getUsername());
        if (userInfoMapper.insert(userInfo) == 0) {
            throw new BusinessException("注册失败");
        }
        String token = JwtUtil.generateToken(userAuth.getId().toString());
        redisComponent.set(userAuth.getId().toString(), token, 1, TimeUnit.DAYS);
        return token;
    }

    @Override
    public Boolean canRegister(String username) {
        return userAuthMapper.selectCount(new QueryWrapper<UserAuth>().eq("username", username)) == 0;
    }
}
