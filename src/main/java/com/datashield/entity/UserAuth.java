package com.datashield.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User 认证实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user_auth")
public class UserAuth {
    /**
     * 主键 ID, 雪花算法生成
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码, sha256 加密, 长度 64
     */
    private String password;
}
