package com.github.mq.simple.consumer;

import com.github.common.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
public class SimpleQueueConsumer {
    private final static String QUEUE_NAME = "SIMPLE_QUEUE_TEST_01";
    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        /**
         * 监听队列
         * queue: 队列名词
         * autoAck: 是否自动确认消息,true自动确认,false不自动要手动调用,建立设置为false
         * callback: 消费者对象的接口
         */
        channel.basicConsume(QUEUE_NAME, true, consumer);
        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
        }
    }
}
