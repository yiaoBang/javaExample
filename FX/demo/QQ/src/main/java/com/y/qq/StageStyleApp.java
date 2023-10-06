package com.y.qq;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 舞台风格应用
 *
 * @author Y
 * @date 2023/01/29
 */
public class StageStyleApp extends Application {


    @Override
    public void start(Stage stage) throws Exception {

        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(new Scene(new BorderPane(new Button("测试按钮")), 1, 1));
        stage.setX(Double.MAX_VALUE);
        stage.setTitle("标题");
        stage.show();


        Stage myStage = new Stage(StageStyle.TRANSPARENT);
        myStage.initOwner(stage);
        myStage.setScene(new Scene(new BorderPane(new Button("测试按钮")), 300, 300));
        myStage.show();
    }
}
