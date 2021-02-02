package top.mengchao.kafka.manualoffset;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import top.mengchao.kafka.Util;

import java.time.Duration;
import java.util.*;

/**
 * @title：
 * @author: mengchaob
 * @date: 2021年01月21日 19:23
 * @description:
 * 出现手动提交的原因
 * 背景：consumer.poll(Duration.ofMillis(100)) 长连接100毫秒轮训拉去broker的数据
 * 1.拉去数据后，处理数据时异常，但是可能已经提交offerset了，会导致部分数据无法被消费
 * 2.设置的提交时间过长，处理完数据后，系统异常，提交offerset失败，导致数据重复消费
 */
public class Consumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", Util.SERVERS);
        props.put("acks", Util.ACKS);
        props.setProperty("key.deserializer",Util.KEYSDEERIALIZER);
        props.setProperty("value.deserializer", Util.VALUESDEERIALIZER);
        props.put("group.id", "test");
        /**
         * false人工提交,人工提交的时候，自动提交时间间隔配置失效
         * true 自动提交
         */
        props.setProperty("enable.annotation.commit", "false");
        /**
         * 自动提交的时间间隔
         */
        props.put("annotation.commit.interval.ms", "1000");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(Util.TOPICNAME, Util.TOPICNAME1));
        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
        final int minBatchSize = 10;
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
            }
            if (buffer.size() >= minBatchSize) {
                insertIntoDb(buffer);
                /**
                 * 同步提交
                 */
                //consumer.commitSync();
                /**
                 * 异步提交
                 */
                consumer.commitAsync(new OffsetCommitCallback(){
                    @Override
                    public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {
                        if(e!=null){
                            System.out.println("提交失败，应该尝试提交重试！");
                        }else {
                            map.forEach((topicPartition,offsetAndMetadata)->{
                                System.out.println("主题："+topicPartition.topic()+",分区："+topicPartition.partition()+",偏移量："+offsetAndMetadata.offset()+"。提交成功");
                            });

                        }
                    }
                });
                buffer.clear();
            }
        }
    }
    public static void insertIntoDb(List<ConsumerRecord<String, String>> buffer){
        System.out.println("批量入库开始！");
        for (int i = 0; i < buffer.size(); i++) {
            ConsumerRecord record = buffer.get(i);
            System.out.printf("topic=%s,partition=%d, key = %s, value = %s%n", record.topic(),record.partition(), record.key(), record.value());
        }
        System.out.println("批量入库结束！");
    }
}
