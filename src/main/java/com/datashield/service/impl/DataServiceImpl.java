package com.datashield.service.impl;

import com.datashield.entity.UserInfo;
import com.datashield.exception.BusinessException;
import com.datashield.mapper.DataMapper;
import com.datashield.service.DataService;
import com.datashield.util.UserContextUtil;
import com.datashield.util.UserSqlConnectionUtil;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * 用户上传的数据服务实现类
 */
@Service
public class DataServiceImpl implements DataService {
    @Autowired
    private DataMapper dataMapper;

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
    public List<String> getAllDatabases() {
        UserInfo userInfo = UserContextUtil.getUser();
        return dataMapper.getAllDatabases(userInfo.getId().toString() + "_");
    }

    @Override
    public List<String> getAllTables(String dbName) {
        UserInfo userInfo = UserContextUtil.getUser();
        return dataMapper.getAllTables(userInfo.getId().toString() + "_" + dbName);
    }

    @Override
    public List<String> getColumns(String dbName, String tbName) {
        UserInfo userInfo = UserContextUtil.getUser();
        dbName = userInfo.getId().toString() + "_" + dbName;
        return dataMapper.getColumns(dbName, tbName);
    }

    @Override
    public List<Map<String, Object>> getRecords(String dbName, String tbName) {
        UserInfo userInfo = UserContextUtil.getUser();
        dbName = userInfo.getId().toString() + "_" + dbName;
        return dataMapper.getRecords(dbName, tbName);
    }

    @Override
    public void dropDatabase(String dbName) {
        UserInfo userInfo = UserContextUtil.getUser();
        dbName = userInfo.getId().toString() + "_" + dbName;
        dataMapper.dropDatabase(dbName);
    }
}
