package com.datashield.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datashield.entity.UserAuth;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户认证表映射接口
 */
@Mapper
public interface UserAuthMapper extends BaseMapper<UserAuth> {
}
