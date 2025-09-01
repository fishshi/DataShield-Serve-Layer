package com.datashield.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.datashield.enums.DataMaskRuleEnum;
import com.datashield.enums.TaskStatusEnum;

/**
 * 任务实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_task")
public class Task {
    /**
     * 主键 ID, 雪花算法生成
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 是否为远程数据库
     */
    private Integer isRemote;

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 表名称
     */
    private String dbTable;

    /**
     * 需要脱敏字段名称, 多个字段用逗号分隔
     */
    private String dbColumns;

    /**
     * 脱敏规则, {@link DataMaskRuleEnum}
     */
    private Integer maskRule;

    /**
     * 结果输出表名称
     */
    private String targetTable;

    /**
     * 任务状态, {@link TaskStatusEnum}
     */
    private Integer status;
}
