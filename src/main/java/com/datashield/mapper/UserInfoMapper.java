package com.datashield.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datashield.entity.UserInfo;

/**
 * 用户信息表映射接口
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
