package com.springboot.kafka.spring.admin;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.Scanner;

/**
 * @title：
 * @author: mengchaob
 * @date: 2021年01月25日 21:49
 * @description:
 */
public class Admin {
    public static void main(String[] args) {

        ApplicationContext context = new FileSystemXmlApplicationContext(
                new String[] { "classpath:spring-admin.xml" });
        Scanner sc = new Scanner(System.in);
        sc.next();
    }
}
