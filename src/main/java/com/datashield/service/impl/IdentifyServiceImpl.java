package com.datashield.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.datashield.component.KafkaComponent;
import com.datashield.entity.Identify;
import com.datashield.exception.BusinessException;
import com.datashield.mapper.IdentifyMapper;
import com.datashield.service.IdentifyService;
import com.datashield.util.UserContextUtil;

/**
 * 敏感数据识别服务实现类
 */
@Service
public class IdentifyServiceImpl implements IdentifyService {
    @Autowired
    private IdentifyMapper identifyMapper;
    @Autowired
    private KafkaComponent kafkaComponent;

    @Override
    public void createTask(Identify identify) {
        Long userId = UserContextUtil.getUser().getId();
        identify.setUserId(userId);
        identify.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        if (identifyMapper.insert(identify) == 0) {
            throw new BusinessException("创建敏感数据识别任务失败");
        }
        kafkaComponent.sendMessage("identify-topic", identify.getId().toString());
    }

    @Override
    public List<Identify> queryTaskByUser(Long userId) {
        List<Identify> list = identifyMapper.selectList(new QueryWrapper<Identify>().eq("user_id", userId));
        return list;
    }

    @Override
    public void updateTask(Identify identify) {
        Long userId = UserContextUtil.getUser().getId();
        identify.setUserId(userId);
        identify.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        if (identifyMapper.updateById(identify) == 0) {
            throw new BusinessException("更新敏感数据识别任务失败");
        }
        kafkaComponent.sendMessage("identify-topic", identify.getId().toString());
    }

    @Override
    public void deleteTask(Long id) {
        if (identifyMapper.deleteById(id) == 0) {
            throw new BusinessException("删除敏感数据识别任务失败");
        }
    }

}
