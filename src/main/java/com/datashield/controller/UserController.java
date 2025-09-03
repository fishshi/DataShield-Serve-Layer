package com.datashield.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.datashield.entity.UserInfo;
import com.datashield.exception.BusinessException;
import com.datashield.pojo.Result;
import com.datashield.service.UserService;
import com.datashield.util.ResultUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户服务控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        userService.logout();
        return ResultUtil.success();
    }

    @GetMapping("/getUserInfo")
    public Result<UserInfo> getUserInfo() {
        return ResultUtil.success(userService.getUserInfo());
    }

    @PutMapping("/updateUserInfo")
    public Result<String> updateUserInfo(@RequestBody UserInfo userInfo) {
        userService.updateUserInfo(userInfo);
        return ResultUtil.success();
    }

    @PostMapping("/uploadAvatar")
    public Result<String> uploadAvatar(MultipartFile file) {
        // TODO
        throw new BusinessException("未实现");
        // String url = userService.uploadAvatar(file);
        // if (url != null)
        // return ResultUtil.success(url);
        // else
        // return ResultUtil.error("选中失败,请稍后重试或联系系统管理员");
    }

    @PatchMapping("/updateAvatar")
    public Result<String> updateAvatar(@RequestBody UserInfo userInfo) {
        userService.updateAvatar(userInfo.getAvatarUrl());
        return ResultUtil.success();
    }
}
