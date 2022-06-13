package com.github.mq.work.producer;

import com.github.common.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 工作队列模式：消息生产者
 */
public class WorkQueueProducer {
    private final static String QUEUE_NAME = "WORK_QUEUE_TEST_01";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // 发送消息
        for (int i = 1; i <= 10; i++) {
            String message = "hello world " + i;
            System.out.println(" [Work Queue] Sent '" + message + "'");
            //6. 发送消息
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        }
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
