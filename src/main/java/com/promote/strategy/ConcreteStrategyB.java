package com.promote.strategy;

import com.promote.controller.AsyncController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service()
public class ConcreteStrategyB implements Strategy {

    private Logger logger = LoggerFactory.getLogger(ConcreteStrategyB.class);

    @Override
    public void algorithmInterface() {
        logger.info("ConcreteStrategyB");
    }

    @Override
    public void method1(int a) throws Exception {

    }

    @Override
    public void method2(int a) throws Exception {

    }
}
