package top.mengchao.kafka.synchronously;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import top.mengchao.kafka.Util;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @title：
 * @author: mengchaob
 * @date: 2021年01月20日 11:11
 * @description:
 */
public class KafkaProductor {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", Util.SERVERS);
        props.put("acks", Util.ACKS);
        props.put("key.serializer", Util.KEYSERIALIZER);
        props.put("value.serializer", Util.VALUESERIALIZER);
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++) {
            try {
                /**
                 * send方法后面加上get()，异步变同步
                 */
                producer.send(new ProducerRecord<String, String>(Util.TOPICNAME, Integer.toString(i), Integer.toString(i))).get();
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
