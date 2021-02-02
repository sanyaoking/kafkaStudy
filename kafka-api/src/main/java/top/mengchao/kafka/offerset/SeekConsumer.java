package top.mengchao.kafka.offerset;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import top.mengchao.kafka.Util;
import top.mengchao.kafka.transaction.Consumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @title：
 * @author: mengchaob
 * @date: 2021年01月28日 15:30
 * @description:
 */
public class SeekConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", Util.SERVERS);
        props.put("acks", Util.ACKS);
        props.setProperty("key.deserializer",Util.KEYSDEERIALIZER);
        props.setProperty("value.deserializer", Util.VALUESDEERIALIZER);
        props.put("group.id", "test");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,1);
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        /**
         * 从偏移量60开始消费TOPIC_TEST1主题的0分区的数据
         */
        long offset = 60;
        consumer.assign(Arrays.asList(new TopicPartition(Util.TOPICNAME, 0)));
        consumer.seek(new TopicPartition(Util.TOPICNAME,0),offset);
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
        }
    }
}
