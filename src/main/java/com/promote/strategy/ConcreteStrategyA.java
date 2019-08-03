package com.promote.strategy;

import com.promote.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConcreteStrategyA extends ConcreteStrategyBase implements Strategy {

    private Logger logger= LoggerFactory.getLogger(ConcreteStrategyA.class);


    @Override
    public void algorithmInterface() {

        logger.info("ConcreteStrategyA");

    }

    @Override
    public void method1(int a) throws Exception {

    }

    @Override
    public void method2(int a) throws Exception {

    }
}
