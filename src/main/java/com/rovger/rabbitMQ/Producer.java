package com.rovger.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description: TODO
 * @Author weijlu
 * @Date 2019/2/21 14:30
 */
public class Producer {

    public final static String QUEUE_NAME = "rabbitMQ.test";

    public static void main(String[] args) {
        ConnectionFactory factory = null;
        Connection conn = null;
        Channel channel = null;
        try {
            //创建连接工厂
            factory = new ConnectionFactory();
            //设置RabbitMQ相关信息
            factory.setHost("localhost");
            /*factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setPort(15672);*/

            //创建一个新的连接
            conn = factory.newConnection();
            //创建一个通道
            channel = conn.createChannel();
            //声明一个队列：p1:队列名称 p2:是否持久化(true，队列将在服务器重启时生存) p3:是否时独占队列 p4:当消费者客户端连接断开时是否自动删除队列  p5:队列其他参数
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            for (int i=0; i<5; i++) {
                String message = "Hello RabbitMQ_" + i;
                //发送消息到滴队列：p1:交换机名称  p2:队列映射的路由key  p3:消息的其他属性  p4:发送的信息主体
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
                System.out.println("Producer Send: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            //关闭通道和连接
            try {
                channel.close();
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
