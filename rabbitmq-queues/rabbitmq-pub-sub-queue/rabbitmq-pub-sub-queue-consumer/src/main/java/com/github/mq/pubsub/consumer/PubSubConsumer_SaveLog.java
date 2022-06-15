package com.github.mq.pubsub.consumer;

import com.github.common.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class PubSubConsumer_SaveLog {
    private final static String QUEUE_NAME_01 = "PUB_SUB_QUEUE_TEST_01";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
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
                System.out.println(" [Pub/Sub Queue - 01] Received '" + message + " 该操作日志需要保存'");
            }
        };

        channel.basicConsume(QUEUE_NAME_01, true, consumer);

    }
}
