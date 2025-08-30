package com.datashield.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * 用户上传的数据服务接口
 */
public interface DataService {
    /**
     * 执行SQL文件
     *
     * @param sql
     */
    void executeSql(MultipartFile file);

    /**
     * 获取用户所有的数据库名
     *
     * @return 数据库名列表
     */
    List<String> getAllDatabases();

    /**
     * 获取用户的数据库中的所有表名
     *
     * @param dbName 数据库名
     *
     * @return 表名列表
     */
    List<String> getAllTables(String dbName);

    /**
     * 获取用户的数据库的表的所有字段名
     *
     * @param dbName 数据库名
     * @param tbName 表名
     *
     * @return 字段名列表
     */
    List<String> getColumns(String dbName, String tbName);

    /**
     * 获取用户的数据库的表的所有记录
     *
     * @param dbName 数据库名
     * @param tbName 表名
     *
     * @return 记录列表
     */
    List<Map<String, Object>> getRecords(String dbName, String tbName);

    /**
     * 删除数据库
     *
     * @param dbName 数据库名
     */
    public void dropDatabase(String dbName);
}
