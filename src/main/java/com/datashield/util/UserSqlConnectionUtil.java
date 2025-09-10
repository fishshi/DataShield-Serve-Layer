package com.datashield.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.datashield.entity.UserRemoteDatabase;
import com.datashield.enums.DatabaseTypeEnum;
import com.datashield.exception.BusinessException;

/**
 * 用户数据库连接工具类
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
        } else if (userRemoteDatabase.getDbType() == DatabaseTypeEnum.POSTGRESQL.getCode()) {
            jdbcUrl = "jdbc:postgresql://" + userRemoteDatabase.getDbHost() + ":" + userRemoteDatabase.getDbPort()
                    + "/" + userRemoteDatabase.getDbName();
        } else if (userRemoteDatabase.getDbType() == DatabaseTypeEnum.ORACLE.getCode()) {
            jdbcUrl = "jdbc:oracle:thin:@" + userRemoteDatabase.getDbHost() + ":" + userRemoteDatabase.getDbPort()
                    + ":" + userRemoteDatabase.getDbName();
        } else if (userRemoteDatabase.getDbType() == DatabaseTypeEnum.SQLSERVER.getCode()) {
            jdbcUrl = "jdbc:sqlserver://" + userRemoteDatabase.getDbHost() + ":" + userRemoteDatabase.getDbPort()
                    + ";databaseName=" + userRemoteDatabase.getDbName();
        } else {
            throw new BusinessException("不支持的数据库类型");
        }
        return DriverManager.getConnection(jdbcUrl, userRemoteDatabase.getDbUsername(),
                userRemoteDatabase.getDbPassword());
    }

    /**
     * 获取用户数据库主机地址
     *
     * @return 数据库主机地址
     */
    public static String getHost() {
        return host;
    }

    /**
     * 获取用户数据库端口号
     *
     * @return 数据库端口号
     */
    public static String getPort() {
        return port;
    }

    /**
     * 获取用户数据库用户名
     *
     * @return 用户数据库用户名
     */
    public static String getUsername() {
        return username;
    }

    /**
     * 获取用户数据库密码
     *
     * @return 用户数据库密码
     */
    public static String getPassword() {
        return password;
    }
}
