package com.springboot.kafka.spring.annotation;

import com.springboot.kafka.Util;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Scanner;

/**
 * @title：
 * @author: mengchaob
 * @date: 2021年01月28日 22:21
 * @description:
 */
public class Productor {
    public static void main(String[] args) {

        ApplicationContext context = new FileSystemXmlApplicationContext(
                new String[] { "classpath:spring-sendAnnotation.xml" });
        /**
         * 获取kafkaTemplate
         */
        KafkaTemplate kafkaTemplate = context.getBean(KafkaTemplate.class);
        /**
         * 用这个方法，如果topic不存在，spring会新建一个topic
         */
        for (int i = 0; i < 10; i++) {
            ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(Util.TOPICNAME, 0,"key_"+i,"annotation 测试数据:value="+i);
            future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
                @Override
                public void onSuccess(SendResult<Integer, String> result) {
                    System.out.println("发送成功！topic:" + result.getProducerRecord().topic()+",partirion:"+result.getProducerRecord().partition());
                }

                @Override
                public void onFailure(Throwable ex) {
                    ex.printStackTrace();
                    System.out.println("发送失败！");
                }

            });
        }
        for (int i = 0; i < 10; i++) {
            ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(Util.TOPICNAME,"key_"+i,"annotation 测试数据:value="+i);
            future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
                @Override
                public void onSuccess(SendResult<Integer, String> result) {
                    System.out.println("发送成功！topic:" + result.getProducerRecord().topic()+",partirion:"+result.getProducerRecord().partition());
                }

                @Override
                public void onFailure(Throwable ex) {
                    ex.printStackTrace();
                    System.out.println("发送失败！");
                }

            });
        }

        /**
         * 必须有这段代码不然等不到返回信息，程序就结束运行了
         */
        Scanner sc = new Scanner(System.in);
        sc.next();
    }
}
