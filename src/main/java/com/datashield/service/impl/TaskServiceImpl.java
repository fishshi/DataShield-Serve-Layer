package com.datashield.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.datashield.component.KafkaComponent;
import com.datashield.entity.Task;
import com.datashield.exception.BusinessException;
import com.datashield.mapper.TaskMapper;
import com.datashield.service.TaskService;
import com.datashield.util.UserContextUtil;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private KafkaComponent kafkaComponent;

    @Override
    public void createTask(Task task) {
        Long userId = UserContextUtil.getUser().getId();
        task.setUserId(userId);
        if (taskMapper.insert(task) == 0) {
            throw new BusinessException("创建任务失败");
        }
        String taskId = task.getId().toString();
        kafkaComponent.sendMessage("task-topic", taskId);
    }

    @Override
    public List<Task> getAllTasks() {
        Long userId = UserContextUtil.getUser().getId();
        return taskMapper.selectList(new QueryWrapper<Task>().eq("user_id", userId));
    }

    @Override
    public void updateTask(Task task) {
        Long userId = UserContextUtil.getUser().getId();
        if (!task.getUserId().equals(userId) || taskMapper.updateById(task) == 0) {
            throw new BusinessException("更新任务失败");
        }
        String taskId = task.getId().toString();
        kafkaComponent.sendMessage("task-topic", taskId);
    }

    @Override
    public void deleteTask(Long id) {
        Long userId = UserContextUtil.getUser().getId();
        if (taskMapper.delete(new QueryWrapper<Task>().eq("id", id).eq("user_id", userId)) == 0) {
            throw new BusinessException("删除任务失败");
        }
    }

}
