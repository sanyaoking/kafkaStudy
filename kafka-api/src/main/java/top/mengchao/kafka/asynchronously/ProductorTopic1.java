package top.mengchao.kafka.asynchronously;

import org.apache.kafka.clients.producer.*;
import top.mengchao.kafka.Util;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @title：
 * @author: mengchaob
 * @date: 2021年01月21日 21:53
 * @description:
 */
public class ProductorTopic1 {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", Util.SERVERS);
        props.put("acks", Util.ACKS);
        props.put("key.serializer", Util.KEYSERIALIZER);
        props.put("value.serializer", Util.VALUESERIALIZER);
        Producer<String, String> producer = new KafkaProducer<>(props);
//        for (int i = 0; i < 100; i++) {
//            /**
//             * 内部会调用异步方法
//             */
//            producer.send(new ProducerRecord<String, String>(Util.TOPICNAME, Integer.toString(i), Integer.toString(i)));
//        }
        for (int i = 0; i < 100; i++) {
            /**
             * 回调
             */
            final int tmp = i;
            try {
                producer.send(new ProducerRecord<String, String>(Util.TOPICNAME, Integer.toString(i), Integer.toString(i)), new Callback() {
                    public void onCompletion(RecordMetadata metadata, Exception e) {
                        if(e != null) {
                            e.printStackTrace();
                        } else {
                            /**
                             * 通过打印的日志中value的值，可以发现value不是顺序发送，所以不是步发送信息
                             *  在可以在send方法后面调用get()方法，同步发送信息，这样就可以对比出差别来
                             */
                            System.out.println("The offset of the record we just sent is: " + metadata.offset()+",partition:"+metadata.partition()+",value:"+tmp);
                        }
                    }
                })/*.get()*/;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /**
         * 一定要注意关闭资源，否则会引起资源泄露
         */
        producer.close();
    }
}
