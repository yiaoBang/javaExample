package com.Y.weather;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;


/**
 * @author Y
 * @date 2023/01/27
 */
public class SnowApp extends Application {
    static final Image FLAKE_IMG = new Image(SnowApp.class.getResource("/img/weather/flake.png").toExternalForm(),
            10, 90, true, true);

    private final ArrayList<Flake> flakes = new ArrayList<>(2000);
    private final int flakeNum = 2000;
    private final int w = 800;
    private final int h = 800;
    private final Random random = new Random();
    Text textFps = new Text("FPS:0");
    private Pane root;

    @Override
    public void start(Stage stage) throws Exception {
        root = new Pane();
        textFps.setTextOrigin(VPos.TOP);
        textFps.setFill(Color.WHITE);
        textFps.setFont(Font.font(35));
        root.getChildren().add(textFps);
        String bgUrl = getClass().getResource("/img/weather/123.jpg").toExternalForm();
        root.setStyle("-fx-background-image: url('" + bgUrl + "')");

        initSnow();

        stage.setScene(new Scene(root, w, h));
        stage.setTitle("雪花效果");
        stage.show();

        AnimationTimer animationTimer = new AnimationTimer() {

            int times;
            long lastTime = System.nanoTime();
            //1秒 = 十亿纳秒
            long ONE_SCENED = 1_000_000_000;
            double duration;
            int fps;
            boolean reverse;

            @Override
            public void handle(long l) {
                fps++;
                duration += l - lastTime;
                if (duration >= ONE_SCENED) {
                    textFps.setText("FPS:" + fps);
                    //System.out.println(fps);
                    fps = 0;
                    duration -= ONE_SCENED;
                }
                lastTime = l;
                times++;
                if (times % 180 == 0) {
                    reverse = true;
                    times = 0;
                }
                for (Flake flake : flakes) {
                    flake.setRotate((flake.getRotate() + 3) % 360);
                    flake.setY(flake.getY() + flake.getVerSpeed());
                    if (reverse) {
                        flake.setHorSpeed(-flake.getHorSpeed());
                    }
                    flake.setX(flake.getX() + flake.getHorSpeed());
                    if (flake.getY() > h) {
                        flake.setX(random.nextDouble() * w);
                        flake.setVerSpeed(random.nextDouble() * 2.5 + 0.5);
                        flake.setY(0);
                        flake.setScaleX(random.nextDouble() * 0.5 + 0.6);
                        flake.setScaleY(flake.getScaleY());
                        flake.setRotate(random.nextDouble() * 360);
                    }
                    flake.setOpacity(1 - flake.getY() / h);
                }
                reverse = false;
            }
        };
        animationTimer.start();
    }

    private void initSnow() {
        for (int i = 0; i < flakeNum; i++) {
            Flake flake = new Flake();
            flake.setX(random.nextDouble() * w);
            flake.setVerSpeed(random.nextDouble() * 2.5 + 0.5);
            flake.setY(random.nextDouble() * h);
            flake.setScaleX(random.nextDouble() * 0.5 + 0.6);
            flake.setScaleY(flake.getScaleY());
            flake.setRotate(random.nextDouble() * 360);
            //移动幅度 -0.5~0.5
            flake.setHorSpeed(random.nextDouble() - 0.5);
            flake.setOpacity(1 - flake.getY() / h);


            flakes.add(flake);
        }
        root.getChildren().addAll(flakes);
    }

    /**
     * 雪花
     *
     * @author Y
     * @date 2023/01/27
     */
    class Flake extends ImageView {
        private double verSpeed;
        private double horSpeed;

        public Flake() {
            super(
                    FLAKE_IMG
            );
        }

        public double getHorSpeed() {
            return horSpeed;
        }

        public void setHorSpeed(double horSpeed) {
            this.horSpeed = horSpeed;
        }

        public double getVerSpeed() {
            return verSpeed;
        }

        public void setVerSpeed(double verSpeed) {
            this.verSpeed = verSpeed;
        }
    }
}
