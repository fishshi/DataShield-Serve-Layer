package com.datashield.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.datashield.controller.req.UpdatePasswordReq;
import com.datashield.entity.UserAuth;
import com.datashield.pojo.Result;
import com.datashield.service.AuthService;
import com.datashield.util.ResultUtil;
import com.datashield.util.SHA256Util;

/**
 * 用户认证服务控制器
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    /**
     * 用户注册
     *
     * @param user 用户认证信息 {@link UserAuth}
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody UserAuth user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            return ResultUtil.error("用户名或密码不能为空");
        }
        if (!authService.canRegister(user.getUsername())) {
            return ResultUtil.error("用户名已被注册");
        }
        user.setPassword(SHA256Util.sha256(user.getPassword()));
        String token = authService.register(user);
        if (token == null) {
            return ResultUtil.error("注册失败");
        } else {
            return ResultUtil.success(token);
        }
    }

    /**
     * 用户登录
     *
     * @param user 用户认证信息 {@link UserAuth}
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody UserAuth user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            return ResultUtil.error("用户名或密码不能为空");
        }
        user.setPassword(SHA256Util.sha256(user.getPassword()));
        String token = authService.login(user);
        if (token == null) {
            return ResultUtil.error("登陆失败, 用户名或密码错误");
        } else {
            return ResultUtil.success(token);
        }
    }

    /**
     * 检查用户名是否可以注册
     *
     * @param username 用户名
     */
    @GetMapping("/canRegister")
    public Result<Boolean> canRegister(@RequestParam String username) {
        return ResultUtil.success(authService.canRegister(username));
    }

    /**
     * 更新用户密码
     */
    @PatchMapping("/updatePassword")
    public Result<String> updatePassword(@RequestBody UpdatePasswordReq req) {
        req.setOldPassword(SHA256Util.sha256(req.getOldPassword()));
        req.setNewPassword(SHA256Util.sha256(req.getNewPassword()));
        return ResultUtil.success(authService.updatePassword(req.getOldPassword(), req.getNewPassword()));
    }
}
