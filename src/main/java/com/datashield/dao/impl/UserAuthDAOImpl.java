package com.datashield.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.datashield.dao.UserAuthDAO;
import com.datashield.entity.UserAuth;
import com.datashield.mapper.UserAuthMapper;

/**
 * 用户认证 DAO 实现类
 */
@Repository
public class UserAuthDAOImpl implements UserAuthDAO {
    @Autowired
    private UserAuthMapper userAuthMapper;

    @Override
    public UserAuth getUserAuthById(Long id) {
        return userAuthMapper.selectById(id);
    }

    @Override
    public UserAuth getUserAuthByUsername(String username) {
        return userAuthMapper.selectOne(new QueryWrapper<UserAuth>().eq("username", username));
    }

    @Override
    public UserAuth insert(UserAuth user) {
        if (userAuthMapper.insert(user) == 1) {
            return user;
        } else {
            return null;
        }
    }
}