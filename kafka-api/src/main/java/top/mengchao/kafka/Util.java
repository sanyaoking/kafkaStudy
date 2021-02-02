package top.mengchao.kafka;

import java.util.Properties;

/**
 * @title：
 * @author: mengchaob
 * @date: 2021年01月20日 10:59
 * @description:
 * kafka存在具体配置key的java类
 * ProducerConfig
 */
public class Util {
    /**
     * 集群地址，要全部都写上
     */
    public final static String SERVERS="127.0.0.1:9191,127.0.0.1:9192,127.0.0.1:9193";
    /**
     * 确认请求的标准
     * 0 只发送到leader
     * 1 发送到leader并确定leader写入日志
     * 2 发送到leader并确定复制到系统配置的副本数后
     */
    public final static String ACKS = "all";
    /**
     * KEY 序列化类
     */
    public final static String KEYSERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";
    /**
     * KEY 反序列化
     */
    public final static String KEYSDEERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";
    /**
     * VALUE 序列化类
     */
    public final static String VALUESERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";
    /**
     * VALUE 反序列化类
     */
    public final static String VALUESDEERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";
    /**
     * 主题名称
     */
    public final static String TOPICNAME = "TOPIC_TEST1";
    public final static String TOPICNAME1 = "TOPIC_TEST2";
    /**
     * 分区数
     * 在单播模式下，一个消费组内只能由一个消费者消费这个分区，所以消费组内消费者不是越多越好
     */
    public final static int NUMPARTITIONS = 3;
    /**
     * 副本数
     */
    public final static short REPLICATIONFACTOR = 3;

    public final static  Properties props = new Properties();
    static {
        props.put("bootstrap.servers", Util.SERVERS);
        props.put("bootstrap.servers", Util.SERVERS);
        props.put("acks", Util.ACKS);
        props.put("key.serializer", Util.KEYSERIALIZER);
        props.put("value.serializer", Util.VALUESERIALIZER);
    }

}
