package com.promote.config;

import com.promote.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Configurable
public class WebConfig {

    private Logger logger= LoggerFactory.getLogger(WebConfig.class);

    @Bean
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        int core = Runtime.getRuntime().availableProcessors();
        taskExecutor.setCorePoolSize(core);
        taskExecutor.setMaxPoolSize(core*2+1);
        taskExecutor.setKeepAliveSeconds(3);
        taskExecutor.setQueueCapacity(0);
        taskExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                if (!executor.isShutdown()){
                    try {
                        logger.info("getQueue");
                        System.out.println("getQueue");
                        executor.getQueue().put(r);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    Thread.currentThread().interrupt();
                }
            }
        });
        taskExecutor.initialize();
        return taskExecutor;

    }
}
