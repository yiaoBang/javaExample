package com.y;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.time.LocalTimer;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Map;

/**
 * 坦克应用
 *
 * @author Y
 * @date 2023/02/16
 */
public class TankApp extends GameApplication {

    /**
     * 子弹方向
     */
    private final Point2D right = new Point2D(1, 0);
    private final Point2D up = new Point2D(0, -1);
    private final Point2D left = new Point2D(-1, 0);
    private final Point2D down = new Point2D(0, 1);
    private final Duration shootDelay = Duration.seconds(0.25);
    /**
     * 坦克实体
     */
    private Entity tankEntity;
    /**
     * 移动判断
     */
    private boolean isMoving;
    /**
     * 方向
     */
    private Direction dir = Direction.RIGHT;
    private Point2D use = right;
    /**
     * 射击间隔
     */
    private LocalTimer shootTimer;
    private int score;


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * 初始化设置
     *
     * @param gameSettings 游戏设置
     */
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(800);
        gameSettings.setHeight(600);
        gameSettings.setTitle("Tank");
        gameSettings.setVersion("0.1");
        //assets 是所有资源的父目录
        //      textures是专门用于储存图片的目录
        gameSettings.setAppIcon("tank.png");
    }

    /**
     * 在前初始化
     */
    @Override
    protected void onPreInit() {
        //预先加载一些资源
        //设置游戏的初始化音量
        FXGL.getSettings().setGlobalMusicVolume(0.5);
        FXGL.getSettings().setGlobalSoundVolume(0.8);
        FXGL.loopBGM("bg.mp3");
    }

    /**
     * 重写方法,获取用户的输入
     */
    @Override
    protected void initInput() {
        FXGL.getInput().addAction(new UserAction("Move up") {
            @Override
            protected void onAction() {
                if (isMoving) {
                    return;
                }
                dir = Direction.UP;
                isMoving = true;
                tankEntity.translateY(-5);
                tankEntity.setRotation(270);
            }
        }, KeyCode.W);
        FXGL.getInput().addAction(new UserAction("Move right") {
            @Override
            protected void onAction() {
                if (isMoving) {
                    return;
                }
                dir = Direction.RIGHT;
                isMoving = true;
                tankEntity.translateX(5);
                tankEntity.setRotation(0);
            }
        }, KeyCode.D);
        FXGL.getInput().addAction(new UserAction("Move down") {
            @Override
            protected void onAction() {
                if (isMoving) {
                    return;
                }
                dir = Direction.DOWN;
                isMoving = true;
                tankEntity.translateY(5);
                tankEntity.setRotation(90);
            }
        }, KeyCode.S);
        FXGL.getInput().addAction(new UserAction("Move left") {
            @Override
            protected void onAction() {
                if (isMoving) {
                    return;
                }
                dir = Direction.LEFT;
                isMoving = true;
                tankEntity.translateX(-5);
                tankEntity.setRotation(180);
            }
        }, KeyCode.A);
        FXGL.getInput().addAction(new UserAction("Shoot") {
            @Override
            protected void onAction() {
                //判断发射的时间间隔是否达到0.25秒
                if (!shootTimer.elapsed(shootDelay)) {
                    return;
                }
                //计时器清零
                shootTimer.capture();
                switch (dir) {
                    case UP -> use = up;
                    case LEFT -> use = left;
                    case DOWN -> use = down;
                    case RIGHT -> use = right;
                }
                Rectangle rectangle = new Rectangle(20, 20);
                rectangle.setFill(Color.GREEN);
                //子弹实体
                Entity bullet = FXGL.entityBuilder()
                        .type(GameType.BULLET)
                        .at(tankEntity.getCenter().getX() - 10, tankEntity.getCenter().getY() - 10)
                        .viewWithBBox(rectangle)
                        //子弹组件
                        .with(new ProjectileComponent(use, 800))
                        //屏幕外移除组件这个实体
                        .with(new OffscreenCleanComponent())
                        //可碰撞组件
                        .collidable()
                        .buildAndAttach();

            }
        }, KeyCode.J);
    }

    /**
     * 初始化游戏增值
     *
     * @param vars var
     */
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
        vars.put("x", 0.0);

    }

    /**
     * 初始化游戏
     */
    @Override
    protected void initGame() {
        FXGL.getip("score").addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() >= 20) {
                    FXGL.getNotificationService().pushNotification("666");
                }
            }
        });

        shootTimer = FXGL.newLocalTimer();
        Canvas canvas = new Canvas(100, 100);
        GraphicsContext g2D = canvas.getGraphicsContext2D();
        g2D.setFill(Color.web("#0E58EE"));
        g2D.fillRect(0, 0, 80, 30);
        g2D.setFill(Color.web("#062DE3"));
        g2D.fillRect(15, 30, 50, 40);
        g2D.setFill(Color.web("#0E58EE"));
        g2D.fillRect(0, 70, 80, 30);
        g2D.setFill(Color.web("#1B9AFB"));
        g2D.fillRect(40, 40, 60, 20);

        //游戏里面的对象Entity  类似于JAVA Object
        tankEntity = FXGL.entityBuilder()
//                //view决定的是游戏实体的外观
//                .view(canvas)
//                //bbox决定游戏实体的真实大小
//                .bbox(BoundingShape.box(100,100))
                .viewWithBBox(canvas)
                .build();
        tankEntity.setRotationOrigin(new Point2D(50, 50));
        FXGL.getGameWorld().addEntities(tankEntity);
        createEnemy();
//
//        Point2D center = tankEntity.getCenter();
//        System.out.println(center);
//        System.out.println(tankEntity.getWidth());
//        System.out.println(tankEntity.getHeight());
    }

    /**
     * init物理
     */
    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(GameType.BULLET, GameType.ENEMY) {
                    @Override
                    protected void onCollisionBegin(Entity bullet, Entity enemy) {
//                        int score1 = FXGL.geti("score") + 10;
//                        FXGL.set("score", score1);
                        //设置自增
                        FXGL.inc("score", 10);
                        bullet.removeFromWorld();
                        Point2D center = enemy.getCenter();
                        enemy.removeFromWorld();
                        Duration seconds = Duration.seconds(.3);
                        Circle circle = new Circle(30, Color.RED);
                        //创建一个爆炸实体
                        Entity boom = FXGL.entityBuilder()
                                .at(center)
                                .view(circle)
                                //过期自动清除的组件
                                .with(new ExpireCleanComponent(seconds))
                                .buildAndAttach();

                        ScaleTransition st = new ScaleTransition(seconds, circle);
                        st.setToX(10);
                        st.setToY(10);

                        FadeTransition ft = new FadeTransition(seconds, circle);
                        ft.setToValue(0);

                        ParallelTransition pt = new ParallelTransition(st, ft);
                        //移除爆炸组件
//                        pt.setOnFinished(event -> {
//                            boom.removeFromWorld();
//                        });
                        pt.play();

                        createEnemy();

                    }
                });
    }

    /**
     * 初始化用户界面
     */
    @Override
    protected void initUI() {
//        Text text = FXGL.addVarText("score", 20, 20);
//        text.setFill(Color.BLACK);
//        text.fontProperty().unbind();
//        text.setFont(Font.font(25));
        //FXGL.addUINode(text);

        Text text = FXGL.getUIFactoryService()
                .newText(FXGL.getip("score").asString("得分:%d"));
        text.setLayoutX(25);
        text.setLayoutY(25);
        text.setFill(Color.ORANGE);
        text.fontProperty().unbind();
        text.setFont(Font.font(25));
        FXGL.addUINode(text);

    }

    /**
     * 在更新
     */
    @Override
    protected void onUpdate(double tpf) {
        //每一帧消耗的时间
        // System.out.println(tpf);
        isMoving = false;
        //System.out.println(FXGL.getGameWorld().getEntities().size());

    }

    private void createEnemy() {
        FXGL.entityBuilder()
                .type(GameType.ENEMY)
                .at(FXGLMath.random(60, 800 - 60), FXGLMath.random(60, 600 - 60))
                .viewWithBBox(new Rectangle(60, 60, Color.BLUE))
                //可碰撞组件
                .collidable()
                .buildAndAttach();
    }
}


