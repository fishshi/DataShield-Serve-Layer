package com.datashield.component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.datashield.entity.Task;
import com.datashield.enums.ScheduleTypeEnum;
import com.datashield.mapper.TaskMapper;

/**
 * 周期性任务调度器
 */
@Component
public class TaskScheduler {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private KafkaComponent kafkaComponent;

    /**
     * 扫描并分发任务, 每天 0 点执行一次
     */
    @Scheduled(cron = "0 0 0 * * ?") // 每天 0 点执行一次
    public void scanAndDispatchTasks() {
        List<Task> dailyTasks = taskMapper
                .selectList(new QueryWrapper<Task>().eq("schedule_type", ScheduleTypeEnum.DAILY.getCode()));
        for (Task task : dailyTasks) {
            kafkaComponent.sendMessage("task-topic", String.valueOf(task.getId()));
        }
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        if (today == DayOfWeek.SUNDAY) {
            List<Task> weeklyTasks = taskMapper
                    .selectList(new QueryWrapper<Task>().eq("schedule_type", ScheduleTypeEnum.WEEKLY.getCode()));

            for (Task task : weeklyTasks) {
                kafkaComponent.sendMessage("task-topic", String.valueOf(task.getId()));
            }
        }
    }
}