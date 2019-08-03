package com.promote.service.impl;

import com.promote.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.TimeUnit;

@Service
public class AsyncServiceImpl {

    private Logger logger= LoggerFactory.getLogger(AsyncServiceImpl.class);

    public String sayHello() {
        return "sayHello";
    }

    @Async
    public void execute(DeferredResult<String> deferredResult) {
        logger.info(Thread.currentThread().getName()+" execute");
        try {
            TimeUnit.SECONDS.sleep(10);
            deferredResult.setResult("OK");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
