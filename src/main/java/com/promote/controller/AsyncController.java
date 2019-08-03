package com.promote.controller;

import com.promote.response.CommonReturnType;
import com.promote.service.impl.AsyncServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.sql.rowset.BaseRowSet;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

@Controller
@RequestMapping("/async")
@CrossOrigin
public class AsyncController extends BaseController {

    private Logger logger= LoggerFactory.getLogger(AsyncController.class);

    @Autowired
    private AsyncServiceImpl asyncService;

    @GetMapping("/sayHello")
    public String sayHello() {
        return "sayHello";
    }
    @GetMapping("/sayHelloCallable")
    public Callable<String> sayHelloCallable() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info(Thread.currentThread().getName()+" call");
                String say = asyncService.sayHello();
                return say;
            }
        };
        return callable;
    }
    @GetMapping("/sayHelloWebAsync1")
    public WebAsyncTask<String> sayHelloWebAsync1() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info(Thread.currentThread().getName()+" call");
                String say = asyncService.sayHello();
                return say;
            }
        };
        return new WebAsyncTask<>(3000,callable);
    }
    @GetMapping("/sayHelloWebAsync2")
    public WebAsyncTask<String> sayHelloWebAsync2() {
        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(3000, new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info(Thread.currentThread().getName()+" call");
                String say = asyncService.sayHello();
                return say;
            }
        });
        webAsyncTask.onCompletion(new Runnable() {
            @Override
            public void run() {
                logger.info(Thread.currentThread().getName()+" onCompletion");
            }
        });
        webAsyncTask.onTimeout(new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info(Thread.currentThread().getName()+" onTimeout");
                throw new TimeoutException("onTimeout");
            }
        });
        return webAsyncTask;
    }

    @GetMapping("/sayHelloDe")
    public DeferredResult<String> sayHelloWebAsync3() {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        asyncService.execute(deferredResult);
        deferredResult.onCompletion(new Runnable() {
            @Override
            public void run() {
                logger.info(Thread.currentThread().getName()+" onCompletion");
            }
        });
        deferredResult.onTimeout(new Runnable() {
            @Override
            public void run() {
                logger.info(Thread.currentThread().getName()+" onTimeout");
                deferredResult.setErrorResult("onTimeout");
            }
        });
        return deferredResult;
    }

}
