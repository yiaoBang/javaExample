package com.Y.rain;

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
public class RainApp extends Application {
    static final Image RAINDROP_IMG = new Image(RainApp.class.getResource("/img/weather/raindrop.png").toExternalForm(),
            20, 20, true, true);
    static final Image RIPPLE_IMG = new Image(RainApp.class.getResource("/img/weather/ripple.png").toExternalForm(),
            20, 20, true, true);

    private final ArrayList<Rain> rainList = new ArrayList<>(2000);
    private final int rainNum = 700;
    private final int w = 1200;
    private final int h = 900;
    private final Random random = new Random();
    Text textFps = new Text("FPS:0");
    private int poolSize = 480;
    private Pane root;

    @Override
    public void start(Stage stage) throws Exception {
        root = new Pane();
        textFps.setTextOrigin(VPos.TOP);
        textFps.setFill(Color.ORANGE);
        textFps.setFont(Font.font(35));
        root.getChildren().add(textFps);
        String bgUrl = getClass().getResource("/img/weather/rain_bg.png").toExternalForm();
        root.setStyle("-fx-background-image: url('" + bgUrl + "')");

        initRain();

        stage.setScene(new Scene(root, w, h));
        stage.setTitle("下雨喽");
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
                for (Rain rain : rainList) {
                    if (rain.getStatus() == RainStatus.RAINDROP) {
                        rain.setY(rain.getY() + rain.getVerSpeed());
                        if (reverse) {
                            rain.setHorSpeed(-rain.getHorSpeed());
                        }
                        rain.setX(rain.getX() + rain.getHorSpeed());
                        //如果雨点达到了指定的区域,那么状态应该改成水波
                        if (rain.getY() >= rain.getMaxY()) {
                            rain.changeStatus();

                        }
                    } else {
                        rain.setScaleX(rain.getScaleX() * 1.03);
                        rain.setScaleY(rain.getScaleX());
                        rain.setOpacity(1 - rain.getScaleX() / 3.0);
                        //如果水波缩放达到三倍那么就应该改变状态成为雨点
                        if (rain.getScaleX() >= 3) {
                            rain.setScaleX(1.0);
                            rain.setScaleY(1.0);
                            rain.setOpacity(1.0);
                            rain.setX(random.nextDouble() * w);
                            rain.setHorSpeed(random.nextDouble() - 0.5);
                            rain.setVerSpeed(random.nextDouble() * 2.5 + 0.5);
                            rain.setMaxY(h - poolSize + random.nextDouble() * poolSize);
                            rain.setY(0);
                            rain.changeStatus();
                        }
                    }
                }
                reverse = false;

            }
        };
        animationTimer.start();
    }

    private void initRain() {
        for (int i = 0; i < rainNum; i++) {
            Rain rain = new Rain();
            rain.setX(random.nextDouble() * w);
            rain.setVerSpeed(random.nextDouble() * 2.5 + 0.5);
            rain.setY(random.nextDouble() * h);

            //移动幅度 -0.5~0.5
            rain.setHorSpeed(random.nextDouble() - 0.5);
            rain.setMaxY(h - poolSize + random.nextDouble() * poolSize);

            rainList.add(rain);
        }
        root.getChildren().addAll(rainList);
    }

    /**
     * 雨状态
     *
     * @author Y
     * @date 2023/01/27
     */
    enum RainStatus {
        /**
         * 雨滴
         */
        RAINDROP,
        /**
         * 涟漪
         */
        RIPPLE
    }

    /**
     * 雨滴
     *
     * @author Y
     * @date 2023/01/27
     */
    class Rain extends ImageView {
        private double verSpeed;
        private double horSpeed;
        private RainStatus status;
        private double maxY;

        public Rain() {
            super(
                    RAINDROP_IMG
            );
            this.status = RainStatus.RAINDROP;
        }

        public double getMaxY() {
            return maxY;
        }

        public void setMaxY(double maxY) {
            this.maxY = maxY;
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

        public RainStatus getStatus() {
            return status;
        }

        public void setStatus(RainStatus status) {
            this.status = status;
        }

        /**
         * 改变状态
         */
        public void changeStatus() {
            this.status = (this.status == RainStatus.RIPPLE ? RainStatus.RAINDROP : RainStatus.RIPPLE);
            if (status == RainStatus.RAINDROP) {
                setImage(RAINDROP_IMG);
            } else {
                setX(getX() - RIPPLE_IMG.getWidth() / 2);
                setImage(RIPPLE_IMG);
            }
        }
    }
}
