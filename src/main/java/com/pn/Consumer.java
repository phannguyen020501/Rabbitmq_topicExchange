package com.pn;

import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    private TopicExchangeChannel channel;

    public void start() throws IOException, TimeoutException {
        //create conection
        Connection connection = ConnectionManager.createConnection();

        //create channel
        channel = new TopicExchangeChannel(connection, Constant.EXCHANGE_NAME);

        //create topic exchange
        channel.declareExchange();

        //creat queues
        channel.declareQueues(Constant.GENERAL_QUEUE_NAME, Constant.JAVA_QUEUE_NAME);

        //binding queues with routing key
        channel.performQueuesBinding(Constant.JAVA_QUEUE_NAME, Constant.JAVA_ROUTING_KEY);
        channel.performQueuesBinding(Constant.GENERAL_QUEUE_NAME, Constant.GPCODER_ROUTING_KEY);

    }

    public void subscribe() throws IOException {
        //subscribe message
        channel.subscribeMessage(Constant.JAVA_QUEUE_NAME);
        channel.subscribeMessage(Constant.GENERAL_QUEUE_NAME);
    }
}
