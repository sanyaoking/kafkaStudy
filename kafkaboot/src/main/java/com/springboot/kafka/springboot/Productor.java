package com.springboot.kafka.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * @title：
 * @author: mengchaob
 * @date: 2021年02月02日 16:24
 * @description:
 */
@Component
public class Productor implements ProductorI{

    public Productor() {
    }
    @Autowired
    KafkaTemplate kafkaTemplate;
    @Value("${order.product.topic.name}")
    private String orderTopic;
    @Value("${order.product.topic.partition}")
    private int orderPartition;


    @Transactional(rollbackFor = RollBackException.class,noRollbackFor = NoRollBackException.class)
    @Override
    public void init() throws NoRollBackException {
        /**
         * 第一个参数主题名称
         * 第二个参数分区
         * 第三个参数key（在调用send其他接口的时候，）
         * 第四个参数需要传输的数据
         */
        for(int i=0;i<10;i++) {
            if(i==8){
                throw new NoRollBackException("springboot kafka 异常测试！ NoRollBackException  不会回滚");
            }
            kafkaTemplate.send(orderTopic, orderPartition, "key_"+i, "KafkaTemplate通过java发送数据 info:"+i);
        }
    }
    @Transactional(rollbackFor = RollBackException.class,noRollbackFor = NoRollBackException.class)
    @Override
    public void init1() throws RollBackException {
        /**
         * 第一个参数主题名称
         * 第二个参数分区
         * 第三个参数key（在调用send其他接口的时候，）
         * 第四个参数需要传输的数据
         */
        for(int i=0;i<10;i++) {
            if(i==8){
                throw new RollBackException("springboot kafka 异常测试！ RollBackException  回滚");
            }
            kafkaTemplate.send(orderTopic, orderPartition, "key_"+i, "init1 KafkaTemplate通过java发送数据 info:"+i);
        }
    }
}
