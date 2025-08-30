package com.datashield.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.datashield.entity.UserAuth;
import com.datashield.pojo.Result;
import com.datashield.service.AuthService;
import com.datashield.util.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户认证服务控制器
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public Result<String> register(@RequestBody UserAuth user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            return ResultUtil.error("用户名或密码不能为空");
        }
        if (!authService.canRegister(user.getUsername())) {
            return ResultUtil.error("用户名已被注册");
        }
        String token = authService.register(user.getUsername(), user.getPassword());
        if (token == null) {
            return ResultUtil.error("注册失败");
        } else {
            return ResultUtil.success(token);
        }
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody UserAuth user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            return ResultUtil.error("用户名或密码不能为空");
        }
        String token = authService.login(user.getUsername(), user.getPassword());
        if (token == null) {
            return ResultUtil.error("登陆失败, 用户名或密码错误");
        } else {
            return ResultUtil.success(token);
        }
    }

    @GetMapping("/canRegister")
    public Result<Boolean> canRegister(@RequestParam String username) {
        return ResultUtil.success(authService.canRegister(username));
    }
}
