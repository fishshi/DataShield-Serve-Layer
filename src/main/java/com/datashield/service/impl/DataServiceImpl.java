package com.datashield.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.datashield.entity.UserInfo;
import com.datashield.entity.UserRemoteDatabase;
import com.datashield.exception.BusinessException;
import com.datashield.mapper.DataMapper;
import com.datashield.mapper.RemoteDataMapper;
import com.datashield.service.DataService;
import com.datashield.util.UserContextUtil;
import com.datashield.util.UserSqlConnectionUtil;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户上传的数据服务实现类
 */
@Service
public class DataServiceImpl implements DataService {
    @Autowired
    private DataMapper dataMapper;
    @Autowired
    private RemoteDataMapper remoteDataMapper;

    @Override
    public void executeSql(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException("上传的文件不能为空");
        }
        UserInfo userInfo = UserContextUtil.getUser();
        try (Connection conn = UserSqlConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setAutoCommit(false);
            runner.setStopOnError(true);
            runner.setSendFullScript(false);
            String sqlContent = new String(file.getBytes(), StandardCharsets.UTF_8);
            sqlContent = sqlContent
                    .replaceAll("(?i)DROP\\s+DATABASE(?:\\s+IF\\s+EXISTS)?\\s+`?([a-zA-Z0-9_]+)`?",
                            "DROP DATABASE IF EXISTS `" + userInfo.getId().toString() + "_$1`")
                    .replaceAll("(?i)CREATE\\s+DATABASE\\s+`?([a-zA-Z0-9_]+)`?",
                            "CREATE DATABASE `" + userInfo.getId().toString() + "_$1`")
                    .replaceAll("(?i)USE\\s+`?([a-zA-Z0-9_]+)`?",
                            "USE `" + userInfo.getId().toString() + "_$1`");
            try (Reader reader = new StringReader(sqlContent)) {
                runner.runScript(reader);
            } catch (Exception e) {
                conn.rollback();
                throw new BusinessException("执行SQL文件失败");
            }
            conn.commit();
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                throw (BusinessException) e;
            } else {
                throw new BusinessException("获取数据库连接失败");
            }
        }
    }

    @Override
    public String generateSqlScript(String dbName) {
        UserInfo userInfo = UserContextUtil.getUser();
        String fullDbName = userInfo.getId().toString() + "_" + dbName;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();

            String host = UserSqlConnectionUtil.getHost();
            String port = UserSqlConnectionUtil.getPort();
            String username = UserSqlConnectionUtil.getUsername();
            String password = UserSqlConnectionUtil.getPassword();

            // 构建mysqldump命令
            List<String> command = new ArrayList<>();
            command.add("mysqldump");
            command.add("-h" + host);
            command.add("-P" + port);
            command.add("-u" + username);
            command.add("-p" + password);
            command.add("--skip-lock-tables");
            command.add("--single-transaction"); // 使用事务保证一致性
            command.add("--routines"); // 包含存储过程和函数
            command.add("--triggers"); // 包含触发器
            command.add("--events"); // 包含事件
            command.add("--set-charset"); // 设置字符集
            command.add("--result-file=-"); // 输出到标准输出
            command.add(fullDbName);

            processBuilder.command(command);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            String result = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new BusinessException("数据库导出失败，退出码: " + exitCode);
            }
            return result;
        } catch (IOException | InterruptedException e) {
            throw new BusinessException("执行mysqldump命令失败: " + e.getMessage());
        }
    }

    @Override
    public void addRemoteDatabase(UserRemoteDatabase userRemoteDatabase) {
        UserInfo userInfo = UserContextUtil.getUser();
        userRemoteDatabase.setUserId(userInfo.getId());
        if (remoteDataMapper.insert(userRemoteDatabase) != 1) {
            throw new BusinessException("添加远程数据库失败");
        }
    }

    @Override
    public List<String> getLocalDatabases() {
        UserInfo userInfo = UserContextUtil.getUser();
        List<String> databases = dataMapper.getAllDatabases(userInfo.getId().toString() + "_");
        for (int i = 0; i < databases.size(); i++) {
            String dbName = databases.get(i);
            dbName = dbName.replace(userInfo.getId().toString() + "_", "");
            databases.set(i, dbName);
        }
        return databases;
    }

    @Override
    public List<UserRemoteDatabase> getRemoteDatabases() {
        UserInfo userInfo = UserContextUtil.getUser();
        return remoteDataMapper.selectList(new QueryWrapper<UserRemoteDatabase>()
                .lambda().eq(UserRemoteDatabase::getUserId, userInfo.getId()));
    }

    @Override
    public void deleteRemoteDatabase(Long id) {
        UserInfo userInfo = UserContextUtil.getUser();
        if (remoteDataMapper.delete(new QueryWrapper<UserRemoteDatabase>()
                .lambda().eq(UserRemoteDatabase::getUserId, userInfo.getId())
                .eq(UserRemoteDatabase::getId, id)) != 1) {
            throw new BusinessException("删除远程数据库失败");
        }
    }

    @Override
    public List<String> getAllTables(String dbName, Boolean isRemote) {
        UserInfo userInfo = UserContextUtil.getUser();
        if (isRemote) {
            UserRemoteDatabase userRemoteDatabase = remoteDataMapper.selectOne(
                    new QueryWrapper<UserRemoteDatabase>().eq("db_name", dbName).eq("user_id", userInfo.getId()));
            if (userRemoteDatabase == null) {
                throw new BusinessException("远程数据库不存在");
            }
            try (Connection conn = UserSqlConnectionUtil.getConnection(userRemoteDatabase)) {
                List<String> tables = new ArrayList<>();
                DatabaseMetaData metaData = conn.getMetaData();
                ResultSet rs = metaData.getTables(dbName, null, "%", new String[] { "TABLE" });
                while (rs.next()) {
                    tables.add(rs.getString("TABLE_NAME"));
                }
                return tables;
            } catch (Exception e) {
                if (e instanceof BusinessException) {
                    throw (BusinessException) e;
                } else {
                    throw new BusinessException("获取数据库连接失败");
                }
            }
        } else {
            return dataMapper.getAllTables(userInfo.getId().toString() + "_" + dbName);
        }
    }

    @Override
    public List<String> getColumns(String dbName, String tbName, Boolean isRemote) {
        UserInfo userInfo = UserContextUtil.getUser();
        if (isRemote) {
            UserRemoteDatabase userRemoteDatabase = remoteDataMapper.selectOne(
                    new QueryWrapper<UserRemoteDatabase>().eq("db_name", dbName).eq("user_id", userInfo.getId()));
            if (userRemoteDatabase == null) {
                throw new BusinessException("远程数据库不存在");
            }
            try (Connection conn = UserSqlConnectionUtil.getConnection(userRemoteDatabase)) {
                List<String> columns = new ArrayList<>();
                DatabaseMetaData metaData = conn.getMetaData();
                try (ResultSet rs = metaData.getColumns(dbName, null, tbName, "%")) {
                    while (rs.next()) {
                        columns.add(rs.getString("COLUMN_NAME"));
                    }
                }
                return columns;
            } catch (Exception e) {
                if (e instanceof BusinessException) {
                    throw (BusinessException) e;
                } else {
                    throw new BusinessException("获取数据库连接失败");
                }
            }
        } else {
            dbName = userInfo.getId().toString() + "_" + dbName;
            return dataMapper.getColumns(dbName, tbName);
        }
    }

    @Override
    public List<Map<String, Object>> getRecords(String dbName, String tbName, Boolean isRemote) {
        UserInfo userInfo = UserContextUtil.getUser();
        if (isRemote) {
            UserRemoteDatabase userRemoteDatabase = remoteDataMapper.selectOne(
                    new QueryWrapper<UserRemoteDatabase>().eq("db_name", dbName).eq("user_id", userInfo.getId()));
            if (userRemoteDatabase == null) {
                throw new BusinessException("远程数据库不存在");
            }
            try (Connection conn = UserSqlConnectionUtil.getConnection(userRemoteDatabase);
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM `" + tbName + "`")) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                List<Map<String, Object>> records = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(metaData.getColumnName(i), rs.getObject(i));
                    }
                    records.add(row);
                }
                return records;
            } catch (Exception e) {
                if (e instanceof BusinessException) {
                    throw (BusinessException) e;
                } else {
                    throw new BusinessException("获取数据库连接失败");
                }
            }
        } else {
            dbName = userInfo.getId().toString() + "_" + dbName;
            return dataMapper.getRecords(dbName, tbName);
        }
    }

    @Override
    public void dropDatabase(String dbName) {
        UserInfo userInfo = UserContextUtil.getUser();
        dbName = userInfo.getId().toString() + "_" + dbName;
        dataMapper.dropDatabase(dbName);
    }
}
