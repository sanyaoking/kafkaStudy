package top.mengchao.kafka.groupIdAndgroupInstanceId;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import top.mengchao.kafka.Util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @title：
 * @author: mengchaob
 * @date: 2021年01月27日 11:49
 * @description:
 */
public class GroupInstanceIDConsumer0 {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", Util.SERVERS);
        props.put("acks", Util.ACKS);
        props.setProperty("key.deserializer",Util.KEYSDEERIALIZER);
        props.setProperty("value.deserializer", Util.VALUESDEERIALIZER);
        props.put("group.id", "test");
        props.put("enable.annotation.commit", "true");
        props.put("annotation.commit.interval.ms", "100");
        /**
         * 设置group.instance.id之后，消费组只能存在唯一一个此id的实例,再启动相同实例名的消费者会导致原消费者报错，自动关闭
         */
        props.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG,"mygroupid");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(Util.TOPICNAME));
        List list = new ArrayList();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.value());
            }
            try {
                /**
                 * 休眠10秒
                 */
                Thread.sleep(10*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
