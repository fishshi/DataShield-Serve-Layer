package com.datashield.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.datashield.entity.UserRemoteDatabase;

/**
 * 用户上传的数据服务接口
 */
public interface DataService {
    /**
     * 执行SQL文件
     *
     * @param sql SQL文件
     */
    void executeSql(MultipartFile file);

    /**
     * 添加用户远程数据库
     *
     * @param userRemoteDatabase 用户远程数据库配置信息
     */
    void addRemoteDatabase(UserRemoteDatabase userRemoteDatabase);

    /**
     * 获取用户所有的数据库名
     *
     * @return 数据库名列表
     */
    List<String> getLocalDatabases();

    /**
     * 获取用户所有的远程数据库配置信息
     * 
     * @return 远程数据库配置信息列表
     */
    List<UserRemoteDatabase> getRemoteDatabases();

    /**
     * 删除远程数据库配置信息
     *
     * @param id 远程数据库配置信息 ID
     */
    void deleteRemoteDatabase(Long id);

    /**
     * 获取用户的数据库中的所有表名
     *
     * @param dbName   数据库名
     * @param isRemote 是否为远程数据库
     *
     * @return 表名列表
     */
    List<String> getAllTables(String dbName, Boolean isRemote);

    /**
     * 获取用户的数据库的表的所有字段名
     *
     * @param dbName   数据库名
     * @param tbName   表名
     * @param isRemote 是否为远程数据库
     *
     * @return 字段名列表
     */
    List<String> getColumns(String dbName, String tbName, Boolean isRemote);

    /**
     * 获取用户的数据库的表的所有记录
     *
     * @param dbName   数据库名
     * @param tbName   表名
     * @param isRemote 是否为远程数据库
     *
     * @return 记录列表
     */
    List<Map<String, Object>> getRecords(String dbName, String tbName, Boolean isRemote);

    /**
     * 删除数据库
     *
     * @param dbName 数据库名
     */
    void dropDatabase(String dbName);
}
