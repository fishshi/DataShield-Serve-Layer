package com.datashield.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datashield.component.RedisComponent;
import com.datashield.service.UserService;
import com.datashield.util.UserContextUtil;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RedisComponent redisComponent;

    @Override
    public void logout() {
        Long id = UserContextUtil.getUser().getId();
        redisComponent.delete(id.toString());
    }
}
