package com.datashield.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datashield.entity.Identify;
import com.datashield.pojo.Result;
import com.datashield.service.IdentifyService;
import com.datashield.util.ResultUtil;
import com.datashield.util.UserContextUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 敏感数据识别任务控制器
 */
@RestController
@RequestMapping("/identify")
public class IdentifyController {
    @Autowired
    private IdentifyService identifyService;

    /**
     * 创建敏感数据识别任务
     */
    @PostMapping("/createIdentify")
    public Result<String> createIdentify(@RequestBody Identify identify) {
        identifyService.createTask(identify);
        return ResultUtil.success();
    }

    /**
     * 查询用户的敏感数据识别任务
     */
    @GetMapping("/getAllIdentify")
    public Result<List<Identify>> getAllIdentify() {
        Long userId = UserContextUtil.getUser().getId();
        return ResultUtil.success(identifyService.queryTaskByUser(userId));
    }

    /**
     * 更新敏感数据识别任务
     */
    @PutMapping("/updateIdentify")
    public Result<String> updateIdentify(@RequestBody Identify identify) {
        identifyService.updateTask(identify);
        return ResultUtil.success();
    }

    /**
     * 删除敏感数据识别任务
     *
     * @param id 敏感数据识别任务ID
     */
    @DeleteMapping("deleteIdentify/{id}")
    public Result<String> deleteIdentify(@PathVariable Long id) {
        identifyService.deleteTask(id);
        return ResultUtil.success();
    }
}
