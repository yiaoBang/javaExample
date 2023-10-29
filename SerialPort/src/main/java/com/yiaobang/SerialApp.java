package com.yiaobang;

import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static com.yiaobang.Serial.SIMPLE_STRING_PROPERTY;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/14 11:10
 */
public class SerialApp extends Application {
    public static void main(String[] args) {
        //文字抗锯齿
        System.setProperty("prism.lcdtext", "false");
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        SerialPort com22 = SerialPort.getCommPort("COM22");
        com22.setComPortParameters(115200, 8, 1, 0);
        com22.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING | SerialPort.TIMEOUT_WRITE_BLOCKING, 200, 200);
        com22.addDataListener(new Serial());
        com22.openPort();
        System.out.println("串口打开成功");


        Label label = new Label("Hello");
        label.setFont(Font.font(80));
        label.setTextFill(Color.RED);
        label.setLayoutX(100);
        label.setLayoutY(50);
        label.textProperty().bind(SIMPLE_STRING_PROPERTY);
        Group group = new Group(label);
        stage.setScene(new Scene(group, 300, 200));
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        Platform.exit();
        System.exit(0);
    }
}
