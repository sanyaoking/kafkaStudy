package top.mengchao.kafka.dispartition;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import top.mengchao.kafka.Util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * @title：
 * @author: mengchaob
 * @date: 2021年01月20日 16:54
 * @description:
 */
public class DispartiConsumerP0 {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", Util.SERVERS);
        props.put("acks", Util.ACKS);
        props.setProperty("key.deserializer",Util.KEYSDEERIALIZER);
        props.setProperty("value.deserializer", Util.VALUESDEERIALIZER);
        props.put("group.id", "test");
        props.put("enable.annotation.commit", "true");
        props.put("annotation.commit.interval.ms", "1000");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        List list = new ArrayList();
        /**
         * 第一个参数是主题名称
         * 第二个参数是分区
         */
        TopicPartition topicPartition = new TopicPartition(Util.TOPICNAME,0);
        list.add(topicPartition);
        /**
         * 指定消费分区
         */
        consumer.assign(list);
        System.out.println("启动P0");
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("topic=%s,partition=%d,offset = %d, key = %s, value = %s%n",record.topic() ,record.partition(),record.offset(), record.key(), record.value());
        }
    }
}
