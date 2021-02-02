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
public class PollConsumer0 {
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
         * 超过15秒，没有心跳自动超时
         */
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,15*1000);
        /**
         * 每次拉去信息，最多从服务器拉取多少条
         * 需要注意的是RECEIVE_BUFFER_CONFIG max.partition.fetch.bytes fetch.min.bytes fetch.max.wait.ms都有可能影响获取到的条数
         * 发送的数据端的发送效率也会影响拉取的条数
         */
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,1);
        /**
         * 设置客户端两次拉取数据的是时间间隔
         * 注意如果当前客户端两次拉取数据的时间超过这个时间间隔，会触发rebalance,重新计算分配partition到不同的客户端
         * 所以要设定合理的值，这个值要考虑到系统的处理信息的时间长度。
         * 当客户端使用非空的group.id的时候，并不会立即重新分配分区，相反，使用者将停止发送心跳，并且等待session.timeout.ms(GROUP_INSTANCE_ID_CONFIG)到期后将重新分配分区。
         */
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,5*1000);
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(Util.TOPICNAME));
        List list = new ArrayList();
        System.out.println("低性能消费者启动，未来可能被踢出partition!");
        consumer.assignment().stream().forEach(e->{
            System.out.println("低性能消费者，消费分区为："+e.partition());
        });
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            System.out.println("======================================");
            consumer.assignment().stream().forEach(e->{
                System.out.println("低性能消费者，消费分区为："+e.partition());
            });
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
