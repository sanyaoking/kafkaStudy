package top.mengchao.kafka.asynchronously;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import top.mengchao.kafka.Util;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @title：
 * @author: mengchaob
 * @date: 2021年01月21日 22:06
 * @description:
 */
public class ConsumerTopic1 {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", Util.SERVERS);
        props.put("acks", Util.ACKS);
        props.setProperty("key.deserializer",Util.KEYSDEERIALIZER);
        props.setProperty("value.deserializer", Util.VALUESDEERIALIZER);
        props.put("group.id", "asynchronously.consumerTopic1");
        props.put("enable.annotation.commit", "true");
        props.put("annotation.commit.interval.ms", "1000");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(Util.TOPICNAME, Util.TOPICNAME1));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        }
    }
}
