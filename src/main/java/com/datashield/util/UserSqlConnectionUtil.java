package com.datashield.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.datashield.entity.UserRemoteDatabase;
import com.datashield.enums.DatabaseTypeEnum;
import com.datashield.exception.BusinessException;

/**
 * 用户数据库连接工具类
 * TODO: 本地改用用户MySQL服务器, 与业务服务器分开
 */
public class UserSqlConnectionUtil {
    private static String host = "127.0.0.1";
    private static String port = "3306";
    private static String username = "root";
    private static String password = "123456";

    /**
     * 获取用户本地 MySQL 数据库连接
     * 
     * @return 数据库连接
     */
    public static Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306";
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    /**
     * 获取用户远程 MySQL 数据库连接
     * 
     * @return 数据库连接
     */
    public static Connection getConnection(UserRemoteDatabase userRemoteDatabase) throws SQLException {
        String jdbcUrl;
        if (userRemoteDatabase.getDbType() == DatabaseTypeEnum.MYSQL.getCode()) {
            jdbcUrl = "jdbc:mysql://" + userRemoteDatabase.getDbHost() + ":" + userRemoteDatabase.getDbPort()
                    + "/" + userRemoteDatabase.getDbName();
        } else {
            throw new BusinessException("暂不支持该数据库类型");
        }
        return DriverManager.getConnection(jdbcUrl, userRemoteDatabase.getDbUsername(),
                userRemoteDatabase.getDbPassword());
    }

    public static String getHost() {
        return host;
    }

    public static String getPort() {
        return port;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }
}
