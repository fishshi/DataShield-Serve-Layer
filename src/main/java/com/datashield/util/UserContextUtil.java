package com.datashield.util;

import com.datashield.entity.UserInfo;

/**
 * 用户上下文工具类
 */
public class UserContextUtil {
    /**
     * 用户本地线程变量
     */
    private static final ThreadLocal<UserInfo> userThreadLocal = new ThreadLocal<>();

    /**
     * 设置用户上下文信息
     *
     * @param user 用户信息
     */
    public static void setUser(UserInfo user) {
        userThreadLocal.set(user);
    }

    /**
     * 获取用户上下文信息
     *
     * @return 用户信息
     */
    public static UserInfo getUser() {
        return userThreadLocal.get();
    }

    /**
     * 清除用户上下文信息
     */
    public static void clear() {
        userThreadLocal.remove();
    }
}
