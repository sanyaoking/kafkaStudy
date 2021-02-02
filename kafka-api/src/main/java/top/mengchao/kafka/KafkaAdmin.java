package top.mengchao.kafka;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.KafkaFuture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @title：
 * @author: mengchaob
 * @date: 2021年01月20日 11:11
 * @description:
 */
public class KafkaAdmin {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", Util.SERVERS);
        props.put("acks", Util.ACKS);
        props.put("key.serializer", Util.KEYSERIALIZER);
        props.put("value.serializer", Util.VALUESERIALIZER);
        KafkaAdminClient kafkaAdminClient = (KafkaAdminClient)Admin.create(props);
        /**
         * 创建topic
         */
        Collection<NewTopic> topics = new ArrayList<>();
        /**
         * 第一个参数 主题明后才能
         * 第二个参数 分区数
         * 第三个参数 副本数
         */
        NewTopic topic1 = new NewTopic(Util.TOPICNAME,Util.NUMPARTITIONS+2,Util.REPLICATIONFACTOR);
        topics.add(topic1);
        NewTopic topic2 = new NewTopic(Util.TOPICNAME1,Util.NUMPARTITIONS,Util.REPLICATIONFACTOR);
        topics.add(topic2);
        kafkaAdminClient.createTopics(topics);

        /**
         * 获取topic信息
         */
        ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
        listTopicsOptions.listInternal(true);
        ListTopicsResult result = kafkaAdminClient.listTopics(listTopicsOptions);
        Collection<TopicListing> list = null;
        try {
            list = result.listings().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(list);
    }
}
