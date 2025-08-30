package com.datashield.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka 组件类
 */
@Component
public class KafkaComponent {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 发送消息到指定主题
     *
     * @param topic   主题名
     * @param message 消息
     */
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}