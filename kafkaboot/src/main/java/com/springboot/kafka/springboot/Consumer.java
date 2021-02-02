package com.springboot.kafka.springboot;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * @title：
 * @author: mengchaob
 * @date: 2021年02月02日 17:52
 * @description:
 */
@Configuration
@Transactional
public class Consumer {
    @KafkaListener(topics = "${order.consumer.topic.name}",topicPattern = "${order.consumer.topic.partition}")
    public void processMessage(String content) {
        System.out.println(content);
    }
}
