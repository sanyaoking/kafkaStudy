package top.mengchao.kafka.offerset;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import top.mengchao.kafka.Util;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @title：
 * @author: mengchaob
 * @date: 2021年01月20日 11:11
 * @description:
 * 拉去条数和时间间隔测试
 * 客户端需要多等时间才能出现结果，正常情况下，如果有4个分区的话，0和1两个消费者会没人摘一个
 */
public class Productor {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", Util.SERVERS);
        props.put("key.serializer", Util.KEYSERIALIZER);
        props.put("value.serializer", Util.VALUESERIALIZER);
        props.put("acks", Util.ACKS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG,"offersetProductor");
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 5; i++) {
            try {
                /**
                 * send方法后面加上get()，异步变同步
                 */
                producer.send(new ProducerRecord<String, String>(Util.TOPICNAME, i%4,Integer.toString(i), "消费者启动获取消息偏移量接口测试，value:"+Integer.toString(i))).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
        /**
         * 一定要注意关闭资源，否则会引起资源泄露
         */
        producer.close();
    }
}
