package com.y.example;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/4 14:24
 */
public class IconApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        FlowPane root = new FlowPane();
        Button button = new Button("书籍");
        button.setFont(Font.font(20));
        button.setTextFill(Color.BLUE);

        FontIcon fontIcon = new FontIcon(BootstrapIcons.BOOK);
        fontIcon.setIconSize(25);
        button.setGraphic(fontIcon);


        Button button2 = new Button();
        button2.setGraphic(new FontIcon());
        button2.getStyleClass().add("button2");

        ToggleButton toggleButton = new ToggleButton("麦克风");
        toggleButton.getStyleClass().add("button3");
        toggleButton.setGraphic(new FontIcon());

        ToggleButton toggleButton2 = new ToggleButton("绑定");
        toggleButton2.getStyleClass().add("button4");
        FontIcon icon = new FontIcon();
        icon.iconCodeProperty().bind(
                Bindings.when(toggleButton2.selectedProperty())
                        .then(AntDesignIconsOutlined.AUDIO)
                        .otherwise(AntDesignIconsOutlined.AUDIO_MUTED)
        );
        toggleButton2.setGraphic(icon);


        root.getChildren().addAll(button, button2, toggleButton, toggleButton2);
        Scene scene = new Scene(root, 300, 100);
        scene.getStylesheets().add(getClass().getResource("/css/icon-app.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Ikonli图标使用");
        stage.show();
    }
}
