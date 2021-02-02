package top.mengchao.kafka.buffer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
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
public class BufferConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", Util.SERVERS);
        props.put("acks", Util.ACKS);
        props.setProperty("key.deserializer",Util.KEYSDEERIALIZER);
        props.setProperty("value.deserializer", Util.VALUESDEERIALIZER);
        props.put("group.id", "test");
        props.put("enable.annotation.commit", "true");
        /**
         * 自动提交间隙多少毫秒
         */
        props.put("annotation.commit.interval.ms", "1000");
        /**
         * 每次拉去信息，最多从服务器拉取多少条
         * 需要注意的是RECEIVE_BUFFER_CONFIG max.partition.fetch.bytes fetch.min.bytes fetch.max.wait.ms都有可能影响获取到的条数
         * 发送的数据端的发送效率也会影响拉取的条数
         */
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,5);
        /**
         * 客户端接受信息的缓存大小，如果设置为-1则取系操作统的默认值
         */
//        props.put(ConsumerConfig.RECEIVE_BUFFER_CONFIG,65536 );
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(Util.TOPICNAME));
        List list = new ArrayList();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            System.out.println("本次拉取"+records.count()+"条信息！");
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("topic=%s,partition=%d,offset = %d, key = %s, value = %s%n",record.topic() ,record.partition(),record.offset(), record.key(), record.value());
        }
    }
}
