package top.mengchao.kafka.pollConfig;

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
public class PollConsumer1 {
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
         * 每次拉去信息，最多从服务器拉取多少条
         * 需要注意的是RECEIVE_BUFFER_CONFIG max.partition.fetch.bytes fetch.min.bytes fetch.max.wait.ms都有可能影响获取到的条数
         * 发送的数据端的发送效率也会影响拉取的条数
         */
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,1);
        /**
         * 设置客户端两次拉取数据的是时间间隔
         * 注意如果当前客户端两次拉取数据的时间超过这个时间间隔，可能导致borker删除这个客户端，为对应的partition分配新的客户端
         * 所以要设定合理的值，这个值要考虑到系统的处理信息的时间长度。
         */
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,5*1000);
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(Util.TOPICNAME));
        System.out.println("高性能消费者启动！");
        List list = new ArrayList();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            System.out.println("======================================");
            consumer.assignment().stream().forEach(e->{
                System.out.println("高性能消费者，消费分区为："+e.partition());
            });
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.value());
            }
            try {
                /**
                 * 休眠1秒
                 */
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
