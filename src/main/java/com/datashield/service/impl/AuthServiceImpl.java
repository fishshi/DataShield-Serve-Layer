package com.datashield.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.datashield.component.RedisComponent;
import com.datashield.dao.UserAuthDAO;
import com.datashield.dao.UserInfoDAO;
import com.datashield.entity.UserAuth;
import com.datashield.entity.UserInfo;
import com.datashield.exception.BusinessException;
import com.datashield.service.AuthService;
import com.datashield.util.JwtUtil;

/**
 * 用户认证服务实现类
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserAuthDAO userAuthDAO;
    @Autowired
    private UserInfoDAO userInfoDAO;
    @Autowired
    private RedisComponent redisComponent;

    @Override
    public String login(String username, String password) {
        UserAuth user = userAuthDAO.getUserAuthByUsername(username);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!user.getPassword().equals(password)) {
            return null;
        }
        String token = JwtUtil.generateToken(user.getId().toString());
        redisComponent.set(user.getId().toString(), token, 1, TimeUnit.DAYS);
        return token;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String register(String username, String password) {
        UserAuth user = new UserAuth();
        user.setUsername(username);
        user.setPassword(password);
        user = userAuthDAO.insert(user);
        if (user == null) {
            throw new BusinessException("注册失败");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(username);
        userInfo = userInfoDAO.insert(userInfo);
        if (userInfo == null) {
            throw new BusinessException("注册失败");
        }
        String token = JwtUtil.generateToken(user.getId().toString());
        redisComponent.set(user.getId().toString(), token, 1, TimeUnit.DAYS);
        return token;
    }

    @Override
    public Boolean canRegister(String username) {
        UserAuth user = userAuthDAO.getUserAuthByUsername(username);
        return user == null;
    }
}
