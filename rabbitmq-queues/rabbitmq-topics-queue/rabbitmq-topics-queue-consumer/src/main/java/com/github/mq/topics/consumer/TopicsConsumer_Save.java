package com.github.mq.topics.consumer;

import com.github.common.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicsConsumer_Save {
    private static final String QUEUE_NAME_SAVE = "test_topic_save";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1. 创建连接 Connection
        Connection connection = ConnectionUtil.getConnection();
        //2. 创建Channel
        Channel channel = connection.createChannel();

        //3. 定义队列的消费者
        Consumer consumer = new DefaultConsumer(channel) {
            /**
             * 当收到消息后，会自动执行该方法
             * @param consumerTag 标识
             * @param envelope 获取一些信息，交换机，路由key...
             * @param properties 配置信息
             * @param body 数据
             */
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) {
                String message = new String(body);
                System.out.println(" [Topics Queue - 02] Received '" + message + " 该操作日志需要保存'");
            }
        };

        //4. 监听队列
        channel.basicConsume(QUEUE_NAME_SAVE, true, consumer);
    }
}
