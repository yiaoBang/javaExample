package com.Y;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Y
 * @date 2023/02/02
 */
@SuppressWarnings("all")
public class ForkliftApp extends Application {
    //1秒 = 十亿纳秒
    final long ONE_SCENED = 1_000_000_000;
    final Image RIGHT_IMG = new Image(ForkliftApp.class.getResource("/img/right.png").toExternalForm(),
            48, 31, false, false);
    final Image LEFT_IMG = new Image(ForkliftApp.class.getResource("/img/left.png").toExternalForm(),
            48, 31, false, false);
    final Image RIGHT_NULL_IMG = new Image(ForkliftApp.class.getResource("/img/right-null.png").toExternalForm(),
            48, 31, false, false);
    final Image LEFT_NULL_IMG = new Image(ForkliftApp.class.getResource("/img/left-null.png").toExternalForm(),
            48, 31, false, false);
    final String backgroundImg = ForkliftApp.class.getResource("/img/background.png").toExternalForm();
    /**
     * 叉车移动速度
     */
    double speed = 3;
    Forklif right = new Forklif(RIGHT_IMG);
    Forklif left = new Forklif(LEFT_IMG);
    Forklif rightNull = new Forklif(RIGHT_NULL_IMG);
    Forklif leftNull = new Forklif(LEFT_NULL_IMG);

    Pane root;
    long lastTime = System.nanoTime();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        root = new Pane();
        //设置背景图片
        root.setStyle("-fx-background-image: url('" + backgroundImg + "')");
        stage.setScene(new Scene(root, 1075, 647));
        stage.setTitle("叉车移动");
        stage.show();
        //设置窗口不可调整
        stage.setResizable(false);
        initShow();

        /**
         * 执行动画的方法  默认每秒调用60次
         */
        AnimationTimer animationTimer = new AnimationTimer() {

            @Override
            public void handle(long l) {
                one(l);
                two(l);
                three(l);
                four(l);
            }
        };
        animationTimer.start();


        right.setLastTime(lastTime);
        rightNull.setLastTime(lastTime);

        left.setLastTime(lastTime);
        leftNull.setLastTime(lastTime);
    }

    private void setLastTime(long lastTime) {
        right.setLastTime(lastTime);
        rightNull.setLastTime(lastTime);

        left.setLastTime(lastTime);
        leftNull.setLastTime(lastTime);
    }

    /**
     * 自上而下第一辆叉车的移动方法
     */
    private void one(long l) {
        switch (leftNull.getDirection()) {
            case TOP:
                if (leftNull.getY() - speed > 18) {
                    leftNull.setY(leftNull.getY() - speed);
                } else {
                    leftNull.setY(18);
                    leftNull.setImage(LEFT_NULL_IMG);
                    leftNull.setDirection(Direction.BOTTOM);
                    leftNull.setLastTime(l);
                }
                break;
            case BOTTOM:
                if (l - leftNull.getLastTime() >= ONE_SCENED) {

                    if (leftNull.getY() + speed < 85) {
                        leftNull.setY(leftNull.getY() + speed);
                    } else {
                        leftNull.setY(85);
                        leftNull.setDirection(Direction.LEFT);
                    }

                }
                break;
            case LEFT:
                if (leftNull.getX() - speed > 250) {
                    leftNull.setX(leftNull.getX() - speed);
                } else {
                    leftNull.setX(250);
                    leftNull.setImage(RIGHT_IMG);
                    leftNull.setDirection(Direction.RIGHT);
                    leftNull.setLastTime(l);
                }
                break;
            case RIGHT:
                if (l - leftNull.getLastTime() >= ONE_SCENED) {
                    if (leftNull.getX() + speed < 790) {
                        leftNull.setX(leftNull.getX() + speed);
                    } else {
                        leftNull.setX(790);
                        leftNull.setImage(LEFT_IMG);
                        leftNull.setDirection(Direction.TOP);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 自上而下第二辆叉车的移动方法
     */
    private void two(long l) {
        switch (right.getDirection()) {
            case TOP:
                if (l - right.getLastTime() >= ONE_SCENED) {
                    if (right.getY() - speed > 100) {
                        right.setY(right.getY() - speed);
                    } else {
                        right.setY(100);
                        right.setDirection(Direction.LEFT);
                    }
                }
                break;
            case BOTTOM:
                if (right.getY() + speed < 386) {
                    right.setY(right.getY() + speed);
                } else {
                    right.setY(386);
                    right.setImage(LEFT_NULL_IMG);
                    right.setDirection(Direction.TOP);
                    right.setLastTime(l);
                }
                break;
            case LEFT:
                if (right.getX() - speed > 250) {
                    right.setX(right.getX() - speed);
                } else {
                    right.setX(250);
                    right.setImage(RIGHT_IMG);
                    right.setDirection(Direction.RIGHT);
                    right.setLastTime(l);
                }
                break;
            case RIGHT:
                if (l - right.getLastTime() >= ONE_SCENED) {
                    if (right.getX() + speed < 666) {
                        right.setX(right.getX() + speed);
                    } else {
                        right.setX(666);
                        right.setImage(LEFT_IMG);
                        right.setDirection(Direction.BOTTOM);
                    }
                }
                break;
            default:
                break;

        }
    }

    /**
     * 自上而下第三辆叉车的移动方法
     */
    private void three(long l) {
        switch (rightNull.getDirection()) {
            case TOP:
                if (rightNull.getX() == 870) {
                    if (rightNull.getY() - speed > 370) {
                        rightNull.setY(rightNull.getY() - speed);
                    } else {
                        rightNull.setY(370);
                        rightNull.setImage(LEFT_IMG);
                        rightNull.setDirection(Direction.BOTTOM);
                        rightNull.setLastTime(l);
                    }
                } else {
                    if (rightNull.getY() - speed > 295) {
                        rightNull.setY(rightNull.getY() - speed);
                    } else {
                        rightNull.setY(295);
                        rightNull.setImage(RIGHT_NULL_IMG);
                        rightNull.setDirection(Direction.BOTTOM);
                        rightNull.setLastTime(l);
                    }
                }

                break;
            case BOTTOM:
                if (l - rightNull.getLastTime() >= ONE_SCENED) {
                    if (rightNull.getX() == 286) {
                        if (rightNull.getY() + speed < 483) {
                            rightNull.setY(rightNull.getY() + speed);
                        } else {
                            rightNull.setY(483);
                            rightNull.setDirection(Direction.RIGHT);
                        }
                    } else {
                        if (rightNull.getY() + speed < 483) {
                            rightNull.setY(rightNull.getY() + speed);
                        } else {
                            rightNull.setY(483);
                            rightNull.setDirection(Direction.LEFT);
                        }
                    }
                }
                break;
            case LEFT:
                if (rightNull.getX() - speed > 286) {
                    rightNull.setX(rightNull.getX() - speed);
                } else {
                    rightNull.setX(286);
                    rightNull.setDirection(Direction.TOP);
                }
                break;
            case RIGHT:
                if (rightNull.getX() + speed < 870) {
                    rightNull.setX(rightNull.getX() + speed);
                } else {
                    rightNull.setX(870);
                    rightNull.setImage(LEFT_NULL_IMG);
                    rightNull.setDirection(Direction.TOP);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 自上而下第四辆叉车的移动方法
     */
    private void four(long l) {
        switch (left.getDirection()) {
            case TOP:
                if (left.getX() == 950) {
                    if (l - left.getLastTime() >= ONE_SCENED) {
                        if (left.getY() - speed > 515) {
                            left.setY(left.getY() - speed);
                        } else {
                            left.setY(515);
                            left.setDirection(Direction.LEFT);
                        }
                    }
                } else {
                    if (left.getY() - speed > 295) {
                        left.setY(left.getY() - speed);
                    } else {
                        left.setY(295);
                        left.setImage(RIGHT_NULL_IMG);
                        left.setDirection(Direction.BOTTOM);
                        left.setLastTime(l);
                    }
                }
                break;
            case BOTTOM:
                if (left.getX() == 286) {
                    if (l - left.getLastTime() >= ONE_SCENED) {
                        if (left.getY() + speed < 515) {
                            left.setY(left.getY() + speed);
                        } else {
                            left.setY(515);
                            left.setDirection(Direction.RIGHT);
                        }
                    }
                } else {
                    if (left.getY() + speed < 577) {
                        left.setY(left.getY() + speed);
                    } else {
                        left.setY(577);
                        left.setImage(LEFT_IMG);
                        left.setDirection(Direction.TOP);
                        left.setLastTime(l);
                    }
                }

                break;
            case LEFT:
                if (left.getX() - speed > 286) {
                    left.setX(left.getX() - speed);
                } else {
                    left.setX(286);
                    left.setDirection(Direction.TOP);
                }

                break;
            case RIGHT:
                if (left.getX() + speed < 950) {
                    left.setX(left.getX() + speed);
                } else {
                    left.setX(950);
                    left.setImage(LEFT_NULL_IMG);
                    left.setDirection(Direction.BOTTOM);
                }

                break;
            default:
                break;
        }
    }

    /**
     * 初始化叉车位置的方法
     */
    private void initShow() {

        right.setX(250);
        right.setY(100);
        right.setDirection(Direction.RIGHT);

        left.setX(950);
        left.setY(577);
        left.setDirection(Direction.TOP);


        rightNull.setX(286);
        rightNull.setY(295);
        rightNull.setDirection(Direction.BOTTOM);

        leftNull.setX(790);
        leftNull.setY(18);
        leftNull.setDirection(Direction.BOTTOM);


        root.getChildren().addAll(right, left, rightNull, leftNull);
    }
}



