package com.github.mq.simple.producer;

import com.github.common.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class SimpleQueueProducer {
    private final static String QUEUE_NAME = "SIMPLE_QUEUE_TEST_01";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        /*
         * 参数：
         * queue: 队列名称
         * durable: 是否声明为一个持久队列，如果设置为true，那么该队列将在服务器重新启动后继续存在
         * exclusive: 是否独占。只能有一个消费者监听这队列,当Connection关闭时，是否删除队列
         * autoDelete: 是否自动删除。当没有Consumer时，自动删除掉
         * arguments: 队列的其他属性
         *
         * 如果没有一个队列名词为"SIMPLE_QUEUE_TEST_01"则会新建一个
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 消息内容
        String message = "Hello World!";
        /*
         * 参数
         1. exchange：交换机名称。简单模式下交换机会使用默认的 ""
         2. routingKey：路由名称
         3. props：配置信息
         4. body：发送消息数据
         */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
