package com.promote.strategy;


import com.promote.service.UserService;
import com.promote.service.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public interface Strategy {


    Logger logger = LoggerFactory.getLogger(Strategy.class);


    default void commonMethod1() {


        logger.info("commonMethod1");

    }

    static void commonMethod2() {
        logger.info("commonMethod2");
    }

    /**
     * 策略方法
     */
    void algorithmInterface();

    public static final int field1 = 0;

    int field2 = 0;

    public abstract void method1(int a) throws Exception;

    void method2(int a) throws Exception;
}
