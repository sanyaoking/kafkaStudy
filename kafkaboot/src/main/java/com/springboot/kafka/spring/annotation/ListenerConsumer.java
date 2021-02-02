package com.springboot.kafka.spring.annotation;

import com.springboot.kafka.Util;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.Acknowledgment;

import java.util.HashMap;
import java.util.Map;

/**
 * @title：
 * @author: mengchaob
 * @date: 2021年01月28日 22:09
 * @description:
 */
@Configuration
@EnableKafka
public class ListenerConsumer {

    /**
     *  直接监听固定的主题中的分区
     * @param data
     */
    @KafkaListener(id = "listenPartition0",topicPartitions = @TopicPartition(topic = Util.TOPICNAME,partitions = "0"),groupId = "test")
    public void listenPartition0(String data){
        System.out.println("listenPartition0 接收到信息："+data);
    }
    /**
     *  直接监听固定的主题中的分区
     * @param data
     */
    @KafkaListener(id = "listenPartition1",topicPartitions = @TopicPartition(topic = Util.TOPICNAME,partitions = "1"),groupId = "test")
    public void listenPartition1(String data){
        System.out.println("listenPartition1 接收到信息："+data);
    }

    /**
     *  监听主题
     *  因为测试时主题和上一个测试主题partition都是一个主题，所以如过不使用不同的消费组会导致同一个partition=0在一个组里消费两次，所以要用不同的组
     *  concurrency=3 覆盖了工厂中的创建一个的参数配置，这里会创建三个消费者；
     *  properties三种形式如下：key=value，key:value，key value；
     *  设置为手动提交，并且container中必须设置AckMode
     * @param data
     */
    @KafkaListener(id = "listenTopic",topics = Util.TOPICNAME,groupId = "testTopic",concurrency = "3",
            properties = {ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG+":false",ConsumerConfig.MAX_POLL_RECORDS_CONFIG+":1"},clientIdPrefix="mengchao_")
    public void listenTopic(String data,Acknowledgment acknowledgment){
        System.out.println("listenTopic 手动提交 接收到信息："+data);
        acknowledgment.acknowledge();
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        /**
         * 创建消费者的数量
         */
        factory.setConcurrency(1);
        /**
         * 选择手动提交offerset时，配置提交方式
         */
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        /**
         * 两次拉取数据的最大时间间隔
         */
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }

    @Bean
    public ConsumerFactory<Integer, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }
    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Util.SERVERS);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,Util.KEYSDEERIALIZER);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,Util.VALUESDEERIALIZER);
        return props;
    }
}
