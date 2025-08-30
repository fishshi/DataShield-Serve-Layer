package com.datashield.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datashield.pojo.Result;
import com.datashield.service.UserService;
import com.datashield.util.ResultUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 用户服务控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/logout")
    public Result<String> logout() {
        userService.logout();
        return ResultUtil.success();
    }
}
