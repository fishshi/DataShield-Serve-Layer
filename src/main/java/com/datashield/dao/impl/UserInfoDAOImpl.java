package com.datashield.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.datashield.dao.UserInfoDAO;
import com.datashield.entity.UserInfo;
import com.datashield.mapper.UserInfoMapper;

/**
 * 用户信息 DAO 实现类
 */
@Repository
public class UserInfoDAOImpl implements UserInfoDAO {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserInfoById(Long id) {
        return userInfoMapper.selectById(id);
    }

    @Override
    public UserInfo getUserInfoByUsername(String username) {
        return userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("username", username));
    }

    @Override
    public UserInfo insert(UserInfo userInfo) {
        if (userInfoMapper.insert(userInfo) == 1) {
            return userInfo;
        } else {
            return null;
        }
    }

    @Override
    public UserInfo updateById(UserInfo userInfo) {
        if (userInfoMapper.updateById(userInfo) == 1) {
            return userInfo;
        } else {
            return null;
        }
    }
}
