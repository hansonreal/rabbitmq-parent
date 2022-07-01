package com.github.mq.routing.producer;

import com.github.common.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RoutingProducer {

    private final static String QUEUE_NAME_01 = "ROUTING_QUEUE_PRINT_LOG";

    private final static String QUEUE_NAME_02 = "ROUTING_QUEUE_SAVE_LOG";

    private final static String EXCHANGE_NAME = "ROUTING_EXCHANGE_DIRECT";


    public static void main(String[] args) throws IOException, TimeoutException {
        //1. 创建连接 Connection
        Connection connection = ConnectionUtil.getConnection();
        //2. 创建Channel
        Channel channel = connection.createChannel();
        //3. 创建交换机 Direct Exchange 直连交换机
        /*
         * exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments)
         * 1. exchange:交换机名称
         * 2. type:交换机类型
         *     DIRECT("direct"),：定向
         *     FANOUT("fanout"),：扇形（广播），发送消息到每一个与之绑定队列。
         *     TOPIC("topic"),通配符的方式
         *     HEADERS("headers");参数匹配
         *
         * 3. durable:是否持久化
         * 4. autoDelete:自动删除
         * 5. internal：内部使用。 一般false
         * 6. arguments：参数
         */
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true, false, false, null);

        //4. 创建队列
        channel.queueDeclare(QUEUE_NAME_01, true, false, false, null);
        channel.queueDeclare(QUEUE_NAME_02, true, false, false, null);

        //5. 建立交换机与队列绑定关系
        /*
         *  queueBind(String queue, String exchange, String routingKey)
         * 参数：
         * 1. queue：队列名称
         * 2. exchange：交换机名称
         * 3. routingKey：路由键，绑定规则
         *   如果交换机的类型为fanout ，routingKey设置为""
         */
        //队列1绑定 error
        channel.queueBind(QUEUE_NAME_01, EXCHANGE_NAME, "error");
        //队列2绑定 info  error  warning
        channel.queueBind(QUEUE_NAME_02, EXCHANGE_NAME, "info");
        channel.queueBind(QUEUE_NAME_02, EXCHANGE_NAME, "error");
        channel.queueBind(QUEUE_NAME_02, EXCHANGE_NAME, "warning");


        String message = "[ERROR]  an exception occurred while calling the deleteUserById method....";
        System.out.println(" [Routing Queue] Sent '" + message + "'");

        //6.发送消息
        channel.basicPublish(EXCHANGE_NAME, "warning", null, message.getBytes());

        //7.关闭通道和连接
        channel.close();
        connection.close();

    }
}
