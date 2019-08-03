package com.promote.service;

import com.promote.controller.itemview.GItBean;
import com.promote.error.BusinessException;
import com.promote.error.EmBusinessError;
import com.promote.validater.ValidationResult;
import com.promote.validater.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
//异步声明,如果在方法表示是异步方法，如果在类表示异步类。
//这里的方法自动被注入使用ThreadPoolTaskExecutor作为TaskExecutor（线程池）
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class GitHookBiz {


    @Autowired
    private ValidatorImpl validator;

    public void save(GItBean gItBean) throws BusinessException {
        ValidationResult result = validator.validate(gItBean);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrorMgs());
        }
    }


    //异步声明,如果在方法处表示是异步方法，如果在类处表示异步类（所有的方法都是异步方法）。
    //这里的方法自动被注入使用ThreadPoolTaskExecutor作为TaskExecutor（线程池）
    @Async
    public void executorAsyncTask(Integer i)
    {
        System.out.println("执行异步：" + i);
    }
}
