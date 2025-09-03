package com.datashield.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson2.JSONObject;
import com.datashield.entity.UserRemoteDatabase;
import com.datashield.exception.BusinessException;
import com.datashield.pojo.Result;
import com.datashield.service.DataService;
import com.datashield.util.ResultUtil;

import java.nio.charset.StandardCharsets;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户上传/查询/管理/下载数据库数据控制器
 */
@RestController
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DataService dataService;

    /**
     * 上传 SQL 脚本, 为用户创建数据库
     */
    @PostMapping("/uploadSql")
    public Result<String> uploadSql(@RequestParam MultipartFile file) {
        dataService.executeSql(file);
        return ResultUtil.success();
    }

    /**
     * 下载数据库 SQL 脚本
     *
     * @param dbName 数据库名
     *
     * @return SQL 脚本文件
     */
    @GetMapping("/downloadSql")
    public ResponseEntity<byte[]> downloadSql(@RequestParam String dbName) {
        try {
            String sqlContent = dataService.generateSqlScript(dbName);
            byte[] sqlBytes = sqlContent.getBytes(StandardCharsets.UTF_8);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", dbName + ".sql");
            headers.setContentLength(sqlBytes.length);

            return new ResponseEntity<>(sqlBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            throw new BusinessException("生成SQL脚本失败: " + e.getMessage());
        }
    }

    /**
     * 添加远程数据库
     *
     * @param userRemoteDatabase 用户远程数据库信息
     */
    @PostMapping("/addRemoteDatabase")
    public Result<String> addRemoteDatabase(@RequestBody UserRemoteDatabase userRemoteDatabase) {
        dataService.addRemoteDatabase(userRemoteDatabase);
        return ResultUtil.success();
    }

    /**
     * 获取用户所有数据库名
     *
     * @return 用户所有数据库名列表
     */
    @GetMapping("/getLocalDatabases")
    public Result<List<String>> getLocalDatabases() {
        return ResultUtil.success(dataService.getLocalDatabases());
    }

    /**
     * 获取用户所有远程数据库信息
     * 
     * @return 用户所有远程数据库信息列表
     */
    @GetMapping("/getRemoteDatabases")
    public Result<List<UserRemoteDatabase>> getMethodName() {
        return ResultUtil.success(dataService.getRemoteDatabases());
    }

    /**
     * 删除远程数据库信息
     *
     * @param id
     */
    @DeleteMapping("/deleteRemoteDatabase/{id}")
    public Result<String> postMethodName(@PathVariable Long id) {
        dataService.deleteRemoteDatabase(id);
        return ResultUtil.success();
    }

    /**
     * 查询数据库中所有表名
     *
     * @param dbName 数据库名
     *
     * @return 数据库中所有表名列表
     */
    @GetMapping("/getAllTables")
    public Result<List<String>> getAllTables(@RequestParam String dbName, @RequestParam Boolean isRemote) {
        return ResultUtil.success(dataService.getAllTables(dbName, isRemote));
    }

    /**
     * 查询数据表中所有字段名
     *
     * @param dbName 数据库名
     * @param tbName 表名
     *
     * @return 数据表中所有字段名列表
     */
    @GetMapping("/getColumns")
    public Result<List<String>> getColumns(@RequestParam String dbName, @RequestParam String tbName,
            @RequestParam Boolean isRemote) {
        return ResultUtil.success(dataService.getColumns(dbName, tbName, isRemote));
    }

    /**
     * 获取数据表中所有记录
     *
     * @param dbName 数据库名
     * @param tbName 表名
     *
     * @return 数据表中所有记录列表, 每条记录为 JSON 格式
     */
    @GetMapping("/getRecords")
    public Result<List<JSONObject>> getRecords(@RequestParam String dbName, @RequestParam String tbName,
            @RequestParam Boolean isRemote) {
        List<Map<String, Object>> records = dataService.getRecords(dbName, tbName, isRemote);
        List<JSONObject> jsonObjects = new ArrayList<>();
        for (Map<String, Object> record : records) {
            JSONObject jsonObject = new JSONObject();
            for (String key : record.keySet()) {
                jsonObject.put(key, record.get(key));
            }
            jsonObjects.add(jsonObject);
        }
        return ResultUtil.success(jsonObjects);
    }

    /**
     * 删除数据库
     *
     * @param dbName 数据库名
     */
    @DeleteMapping("/dropDatabase/{dbName}")
    public Result<String> dropDatabase(@PathVariable String dbName) {
        dataService.dropDatabase(dbName);
        return ResultUtil.success();
    }
}
