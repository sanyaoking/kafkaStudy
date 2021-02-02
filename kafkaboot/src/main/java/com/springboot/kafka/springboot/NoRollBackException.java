package com.springboot.kafka.springboot;

/**
 * @title：
 * @author: mengchaob
 * @date: 2021年02月02日 20:39
 * @description:
 */
public class NoRollBackException extends Exception {
    public NoRollBackException(String s) {
        super(s);
    }

    @Override
    public String getMessage() {
        String tmp = super.getMessage();
        return "NoRollBackException：{\"info\":\" "+tmp+"\"}";
    }
}
