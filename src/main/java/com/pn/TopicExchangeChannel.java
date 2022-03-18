package com.pn;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TopicExchangeChannel {
    private String exchangeName;
    private Channel channel;
    private Connection connection;

    public TopicExchangeChannel(Connection connection, String exchangeName) throws IOException {
        this.connection = connection;
        this.exchangeName = exchangeName;
        this.channel = connection.createChannel();
    }

    public void declareExchange() throws IOException {
        //exchangeDeclare (exchange, builtinExchangeType, durable)
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC, true);
    }

    public void declareQueues(String ...queuesName) throws IOException {
        for(String queue: queuesName){
            // queueDeclare - (queuename, durable, exclusive, autoDelete, arguments)
            channel.queueDeclare(queue, true, false, false, null);
        }
    }

    public void performQueuesBinding(String queueName, String routingKey) throws IOException {
        //create bindings - (queue, exchange, routingKey)
        channel.queueBind(queueName, exchangeName, routingKey);
    }

    public void subscribeMessage(String queueName) throws IOException {
        //basicConsume (queue, deliverCallback, cancelCallback)
        DeliverCallback deliverCallback = (consumerTag, messsage)->{
            System.out.println("[Received] [" + queueName+"]" + consumerTag);
            System.out.println("[Received] [" + queueName+"]" + new String(messsage.getBody()));
        };

        CancelCallback cancelCallback = (consumerTag)->{};
        channel.basicConsume(queueName,deliverCallback, cancelCallback);
    }

    public void publishMessage(String message, String messageKey) throws IOException {
        //basicPublish - (exchange, routing key, basicProperties, body)
        System.out.println("[Send][" + messageKey+"]: " + message);
        channel.basicPublish(exchangeName, messageKey, null,
                        message.getBytes(StandardCharsets.UTF_8));
    }
}
