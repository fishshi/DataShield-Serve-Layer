package com.datashield.service;

import java.util.List;

import com.datashield.entity.Task;

/**
 * 脱敏任务服务接口
 */
public interface TaskService {
    /**
     * 创建任务
     *
     * @param task 新创建的任务
     */
    void createTask(Task task);

    /**
     * 获取当前用户的所有任务
     *
     * @return 任务列表
     */
    List<Task> getAllTasks();

    /**
     * 更新任务
     *
     * @param task 需要更新的任务
     */
    void updateTask(Task task);

    /**
     * 删除任务
     *
     * @param id 任务ID
     */
    void deleteTask(Long id);
}
