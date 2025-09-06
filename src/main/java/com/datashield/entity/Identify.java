package com.datashield.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.datashield.enums.TaskStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_identify")
public class Identify {
    /**
     * 主键 id, 雪花算法生成
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 用户 id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    /**
     * 识别名称
     */
    private String identityName;

    /**
     * 是否远程数据库
     */
    private Integer isRemote;

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 字段名称
     */
    private String colomns;

    /**
     * 状态, {@link TaskStatusEnum}
     */
    private Integer status;
}
