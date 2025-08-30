package com.datashield.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson2.JSONObject;
import com.datashield.pojo.Result;
import com.datashield.service.DataService;
import com.datashield.util.ResultUtil;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
     * 获取用户所有数据库名
     *
     * @return 用户所有数据库名列表
     */
    @GetMapping("/getAllDatabases")
    public Result<List<String>> postMethodName() {
        return ResultUtil.success(dataService.getAllDatabases());
    }

    /**
     * 查询数据库中所有表名
     *
     * @param dbName 数据库名
     *
     * @return 数据库中所有表名列表
     */
    @GetMapping("/getAllTables")
    public Result<List<String>> getAllTables(@RequestParam String dbName) {
        return ResultUtil.success(dataService.getAllTables(dbName));
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
    public Result<List<String>> getColumns(@RequestParam String dbName, @RequestParam String tbName) {
        return ResultUtil.success(dataService.getColumns(dbName, tbName));
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
    public Result<List<JSONObject>> getRecords(@RequestParam String dbName, @RequestParam String tbName) {
        List<Map<String, Object>> records = dataService.getRecords(dbName, tbName);
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
    @GetMapping("/dropDatabase")
    public Result<String> dropDatabase(@RequestParam String dbName) {
        dataService.dropDatabase(dbName);
        return ResultUtil.success();
    }
}
