package com.pn;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class App {
    public static void main(String[] args) throws IOException, TimeoutException {
        //create producer, queues, and binding queues to topic exchange
        Producer producer = new Producer();
        producer.start();

        //publish some message
        producer.send("A new java core topic is published ", Constant.JAVA_CORE_MSG_KEY);
        producer.send("A new java general topic is published ", Constant.JAVA_MSG_KEY);
        producer.send("A new design pattern topic is published ", Constant.DESIGN_PATTERN_MSG_KEY);
        producer.send("Not matching any routing key ", Constant.NOT_MATCHING_MSK_KEY);

        //create consumers, queues, and binding queues to topic exchange
        Consumer consumer = new Consumer();
        consumer.start();
        consumer.subscribe();

    }
}
