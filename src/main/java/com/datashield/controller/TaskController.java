package com.datashield.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datashield.entity.Task;
import com.datashield.pojo.Result;
import com.datashield.service.TaskService;
import com.datashield.util.ResultUtil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * 脱敏任务控制器
 */
@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    /**
     * 创建脱敏任务
     *
     * @param task 需要创建的脱敏任务 {@link Task}
     */
    @PostMapping("/createTask")
    public Result<String> createTask(@RequestBody Task task) {
        taskService.createTask(task);
        return ResultUtil.success();
    }

    /**
     * 获取当前用户的所有脱敏任务
     *
     * @return 当前用户的所有脱敏任务 {@link Task}
     */
    @GetMapping("/getAllTasks")
    public Result<List<Task>> getAllTasks() {
        return ResultUtil.success(taskService.getAllTasks());
    }

    /**
     * 更新指定脱敏任务
     *
     * @param task 需要更新的脱敏任务 {@link Task}
     */
    @PutMapping("/updateTask")
    public Result<String> updateTask(@RequestBody Task task) {
        taskService.updateTask(task);
        return ResultUtil.success();
    }

    /**
     * 删除指定脱敏任务
     *
     * @param id 需要删除的脱敏任务ID
     */
    @DeleteMapping("/deleteTask/{id}")
    public Result<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResultUtil.success();
    }
}
