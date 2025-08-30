package com.datashield.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 用户数据库连接工具类
 * TODO: 改用用户MySQL服务器, 与业务服务器分开
 */
public class UserSqlConnectionUtil {
    /**
     * 获取用户 MySQL 数据库连接
     * 
     * @return 数据库连接
     */
    public static Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306";
        String username = "root";
        String password = "123456";
        return DriverManager.getConnection(jdbcUrl, username, password);
    }
}
