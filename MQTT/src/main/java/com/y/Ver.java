package com.y;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;
import io.vertx.mqtt.MqttClientOptions;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Y
 * @version 1.0
 * @date 2023/11/2 10:42
 */
@Slf4j
public class Ver {
    public static final Vertx vertx = Vertx.vertx();
    public static  MqttClient client;
    public static int i = 100;

    public static void main(String[] args) {
        MqttClientOptions options = new MqttClientOptions().setKeepAliveInterval(60);
        client = MqttClient.create(vertx,options);
        connect();
    }

    public static void connect() {
        String host = "192.168.0.112";
        //host = "127.0.0.1";
        client.exceptionHandler(event -> log.error(event.getMessage()));
        client.connect(1883, host).onComplete(s -> {
            if (s.failed()) {
                log.info("连接失败");
                vertx.setTimer(5000, h -> connect());
            } else {
                send();
                sub();
            }
        });
    }

    public static void sub() {
        client.publishHandler(s -> {
//            log.info("There are new message in topic: " + s.topicName());
            log.info("Content(as string) of the message: " + s.payload().toString());
//            log.info("QoS: " + s.qosLevel());
        }).subscribe("Hello", 0);
    }

    public static void send() {
        vertx.setPeriodic(3000, h -> {
            if (client.isConnected()) {
                client.publish("test",
                        Buffer.buffer("Hello" + (i++)),
                        MqttQoS.valueOf(0),
                        false,
                        false);
            } else {
                vertx.cancelTimer(h);
                connect();
            }
        });

    }
}
