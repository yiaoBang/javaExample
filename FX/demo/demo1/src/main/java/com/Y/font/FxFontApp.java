package com.Y.font;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FxFontApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Label label = new Label("Hello 123 落霞与孤鹜齐飞,秋水共长天一色");
//        label.setFont(new Font("隶书",35));
//        label.setFont(Font.font("隶书", FontWeight.BOLD, FontPosture.ITALIC,35));
        label.setFont(
                Font.loadFont(getClass().getResourceAsStream("/font/Hijrnotes.ttf"), 35));
        stage.setScene(new Scene(new BorderPane(label), 520, 380));
        stage.setTitle("FX font App");
        stage.show();
    }
}
