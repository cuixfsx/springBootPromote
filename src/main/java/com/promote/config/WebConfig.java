package com.promote.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class WebConfig {

    private Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Autowired
    RestTemplateBuilder builder;

    //1.3 之前
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

    @Bean
    public RestTemplate restTemplate(){
        return builder.build();
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        int core = Runtime.getRuntime().availableProcessors();
        taskExecutor.setCorePoolSize(core);
        taskExecutor.setMaxPoolSize(core * 2 + 1);
        taskExecutor.setKeepAliveSeconds(3);
        taskExecutor.setQueueCapacity(0);
        taskExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                if (!executor.isShutdown()) {
                    try {
                        logger.info("getQueue");
                        System.out.println("getQueue");
                        executor.getQueue().put(r);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Thread.currentThread().interrupt();
                }
            }
        });
        taskExecutor.initialize();
        return taskExecutor;
    }

}
