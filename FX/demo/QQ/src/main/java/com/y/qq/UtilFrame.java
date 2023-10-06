package com.y.qq;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UtilFrame extends Application {
    private ArrayList<WritableImage> imgList = new ArrayList<>(500);


    @Override
    public void start(Stage stage) throws Exception {
        Button button = new Button("截图");
        Robot robot = new Robot();
        Label label = new Label("准备");
//        button.setOnAction(event -> {
//            label.setText("开始截图");
//            WritableImage writableImage = robot.getScreenCapture(null, 746, 375, 428, 126);
//            BufferedImage img = SwingFXUtils.fromFXImage(writableImage, null);
//            try {
//                ImageIO.write(img, "png", new File("D:\\Y\\桌面\\截屏测试\\test.png"));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            label.setText("完成..");
//        });

        //作为一个定时处理
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            imgList.add(robot.getScreenCapture(null, 746, 375, 428, 126));
        }));
        //执行多少次
        timeline.setCycleCount(10 * 23);

        timeline.setOnFinished(event -> {
            for (int i = 0; i < imgList.size(); i++) {
                BufferedImage img = SwingFXUtils.fromFXImage(imgList.get(i), null);
                try {
                    ImageIO.write(img, "png", new File("D:\\Y\\桌面\\截屏测试\\login" + i + ".png"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            label.setText("截图完成");
            imgList.clear();
        });
        button.setOnAction(event -> {
            label.setText("开始截图");
            timeline.play();
        });


        BorderPane root = new BorderPane();
        root.setCenter(button);
        root.setBottom(label);
        stage.setTitle("截图序列帧图");
        stage.setX(1000);
        stage.setY(450);
        stage.setScene(new Scene(root, 280, 160));
        stage.show();
    }
}
