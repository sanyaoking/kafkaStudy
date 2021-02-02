package com.springboot.kafka.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.PostConstruct;

/**
 * @title：
 * @author: mengchaob
 * @date: 2021年01月28日 21:59
 * @description:
 */
@SpringBootApplication
public class SpringBootProductor {
    public static void  main(String[]args){
        SpringApplication.run(SpringBootProductor.class,args);
    }
    @Bean
    public ApplicationRunner runner(@Autowired ProductorI productorI) {
        return new ApplicationRunnerImpl(productorI);
    }

    static class ApplicationRunnerImpl implements ApplicationRunner{
        ProductorI productorI;

        public ApplicationRunnerImpl(ProductorI productorI) {
            this.productorI = productorI;
        }

        @Override
        public void run(ApplicationArguments args) throws Exception {
            System.out.println("开始事务回滚测试，1.不回滚异常测试");
            try {
                this.productorI.init();
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("开始事务回滚测试，2.回滚异常测试");
            this.productorI.init1();
        }
    }


}
