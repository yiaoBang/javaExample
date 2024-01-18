package com.y;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;

import java.util.UUID;

/**
 * @author Y
 * @version 1.0
 * @date 2023/11/2 10:24
 */
public class HQ {
    public static void main(String[] args) {
        Mqtt5BlockingClient client = Mqtt5Client.builder()
                .identifier(UUID.randomUUID().toString())
                .serverHost("192.168.0.121")
                .serverPort(1883)
                .buildBlocking();

        client.connect();

        client.toAsync().subscribeWith()
                .topicFilter("hello")
                .qos(MqttQos.AT_LEAST_ONCE)
                .callback(System.out::println)
                .send();

        client.toAsync().publishWith()
                .topic("test")
                .payload("1".getBytes())
                .send();
    }
}
