package com.springboot.kafka.spring.annotation;

import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;

/**
 * @title：
 * @author: mengchaob
 * @date: 2021年01月29日 14:58
 * @description:
 */
public class KafkaListenerErrorHandlerImple implements KafkaListenerErrorHandler {
    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException e) {
        System.out.println("handleError(Message<?> message, ListenerExecutionFailedException e)"+message.getPayload());
        return message;
    }

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
        System.out.println("handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer)"+message.getPayload());
        return message;
    }
}
