package com.datashield.service;

import java.util.List;

import com.datashield.entity.Identify;

/**
 * 敏感数据识别服务接口
 */
public interface IdentifyService {
    /**
     * 创建敏感数据识别任务
     */
    void createTask(Identify identify);

    /**
     * 查询用户的所有识别任务
     */
    List<Identify> queryTaskByUser(Long userId);

    /**
     * 更新识别任务
     */
    void updateTask(Identify identify);

    /**
     * 删除识别任务
     */
    void deleteTask(Long id);
}
