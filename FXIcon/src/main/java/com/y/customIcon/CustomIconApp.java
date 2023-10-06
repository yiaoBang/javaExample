package com.y.customIcon;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/6 11:52
 */
public class CustomIconApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //文字抗锯齿
        System.setProperty("prism.lcdtext", "false");
        BorderPane root = new BorderPane();
        Button btn = new Button("settings");
        btn.setGraphic(new FontIcon(CustomIcon.SETTINGS));
        btn.setFont(new Font(50));
        root.setCenter(btn);
        stage.setScene(new Scene(root));
        stage.setTitle("自定义 Ikonli 图标库");
        stage.show();
    }
}
