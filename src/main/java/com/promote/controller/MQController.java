package com.promote.controller;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.promote.error.BusinessException;
import com.promote.response.CommonReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/mq")
@CrossOrigin
public class MQController {

    private Logger logger= LoggerFactory.getLogger(MQController.class);

    /**使用RocketMq的生产者*/
    //@Autowired
    private DefaultMQProducer defaultMQProducer;
    //@Autowired
    private DefaultMQPushConsumer defaultMQPushConsumer;

    @RequestMapping("/send")
    @ResponseBody
    public CommonReturnType send() throws BusinessException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        String msg = "demo msg test";
        logger.info("开始发送消息："+msg);
        Message sendMsg = new Message("DemoTopic","DemoTag",msg.getBytes());
        //默认3秒超时
        SendResult sendResult = defaultMQProducer.send(sendMsg);
        logger.info("消息发送响应信息："+sendResult.toString());

        return CommonReturnType.create(sendResult);
    }
    @RequestMapping("/receive")
    @ResponseBody
    public CommonReturnType receive() throws BusinessException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        logger.info("开始接收消息：");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(System.currentTimeMillis()+"");
        consumer.subscribe("DemoTopic", "*");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //wrong time format 2017_0422_221800
        consumer.setConsumeTimestamp(System.currentTimeMillis()+"");
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                logger.info("Consumer Started.%n "+msgs.size());
                System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        logger.info("Consumer Started.%n");
        return CommonReturnType.create("Consumer Started.%n "+consumer.getConsumerGroup());
    }
}
