package com.github.mq.simple.consumer;

import com.github.common.util.ConnectionUtil;
import com.rabbitmq.client.*;

public class SimpleQueueConsumer {
    private final static String QUEUE_NAME = "SIMPLE_QUEUE_TEST_01";

    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // 定义队列的消费者
        Consumer consumer = new DefaultConsumer(channel) {
            /**
             * 当收到消息后，会自动执行该方法
             * @param consumerTag 标识
             * @param envelope 获取一些信息，交换机，路由key...
             * @param properties 配置信息
             * @param body 数据
             */
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                String message = new String(body);
                System.out.println(" [Simple Queue] Received '" + message + "'");
            }
        };
        /*
         * 监听队列
         * queue: 队列名词
         * autoAck: 是否自动确认消息,true自动确认,false不自动要手动调用,建立设置为false
         * callback: 消费者对象的接口
         */
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
