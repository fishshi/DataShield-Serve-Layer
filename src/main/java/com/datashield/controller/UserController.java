package com.datashield.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.datashield.entity.UserInfo;
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

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/getUserInfo")
    public Result<UserInfo> getUserInfo() {
        return ResultUtil.success(userService.getUserInfo());
    }

    /**
     * 更新用户信息
     *
     * @param userInfo 用户信息
     */
    @PutMapping("/updateUserInfo")
    public Result<String> updateUserInfo(@RequestBody UserInfo userInfo) {
        userService.updateUserInfo(userInfo);
        return ResultUtil.success();
    }

    /**
     * 上传头像
     *
     * @param file 头像文件
     *
     * @return 上传成功的头像 url
     */
    @PostMapping("/uploadAvatar")
    public Result<String> uploadAvatar(MultipartFile file) {
        return ResultUtil.success(userService.uploadAvatar(file));
    }

    /**
     * 更新头像
     */
    @PatchMapping("/updateAvatar")
    public Result<String> updateAvatar(@RequestBody UserInfo userInfo) {
        userService.updateAvatar(userInfo.getAvatarUrl());
        return ResultUtil.success();
    }
}
