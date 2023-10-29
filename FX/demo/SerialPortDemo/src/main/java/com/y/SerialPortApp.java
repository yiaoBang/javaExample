package com.y;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/22 11:36
 */
public class SerialPortApp extends Application {
    public static void main(String[] args) {
        //文字抗锯齿
        System.setProperty("prism.lcdtext", "false");
        launch(args);
    }

    @Override
    public void init() {

    }

    @Override
    public void start(Stage stage) {
        Thread.currentThread().setUncaughtExceptionHandler(new DefaultExceptionHandler(stage));
        //设置javaFX主题(白色)
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        stage.setTitle("SerialComm");
        stage.show();
    }

    @Override
    public void stop() {
        Platform.exit();
    }

}
