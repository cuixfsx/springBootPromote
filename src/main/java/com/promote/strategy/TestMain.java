package com.promote.strategy;

public class TestMain {
    public static void main(String args[]) {
        //选择使用的策略
        Strategy s = new ConcreteStrategyA();
        Context context = new Context(s);
        context.contextInterface();
    }
}
