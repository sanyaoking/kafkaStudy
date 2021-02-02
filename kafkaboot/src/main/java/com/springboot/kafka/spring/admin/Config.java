package com.springboot.kafka.spring.admin;

import com.springboot.kafka.Util;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @title：
 * @author: mengchaob
 * @date: 2021年01月25日 21:58
 * @description:
 */
@Configuration
public class Config {
    /**
     * 	When using Spring Boot,
     * 	a KafkaAdmin bean is automatically registered so you only need the NewTopic @Bean s
     * @return
     */
    @Bean
    public KafkaAdmin admin() {
        /**
         * kafka客户端查看topic命令
         * kafka-topics.bat --list --zookeeper localhost:2181
         */
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,Util.SERVERS);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("thing1")
                .partitions(10)
                .replicas(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name("thing2")
                .partitions(10)
                .replicas(3)
                .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
                .build();
    }

    @Bean
    public NewTopic topic3() {
        return TopicBuilder.name("thing3")
                .assignReplicas(0, Arrays.asList(0, 1))
                .assignReplicas(1, Arrays.asList(1, 2))
                .assignReplicas(2, Arrays.asList(2, 0))
                .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
                .build();
    }

    @Bean
    public NewTopic topic4() {
        return TopicBuilder.name("defaultBoth")
                .build();
    }

    @Bean
    public NewTopic topic5() {
        return TopicBuilder.name("defaultPart")
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic6() {
        return TopicBuilder.name("defaultRepl")
                .partitions(3)
                .build();
    }

}
