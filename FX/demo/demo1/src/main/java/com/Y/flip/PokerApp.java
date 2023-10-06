package com.Y.flip;

import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

/**
 * 扑克应用
 *
 * @author Y
 * @date 2023/01/28
 */
public class PokerApp extends Application {
    /**
     * 旋转方式
     */
    private final Flip flip = Flip.Y;
    private final String pokerUrl = getClass().getResource("/img/flip/poker.png").toExternalForm();
    private final Random random = new Random();
    private Pane frontPane;
    /**
     * 记录当前状态是否正在翻转
     */
    private boolean flipping;
    /**
     * 旋转的方向
     */
    private boolean dir;

    @Override
    public void start(Stage stage) throws Exception {

        int i = random.nextInt(4) + 1;
        String bgUrl;
        switch (i) {
            case 1:
                bgUrl = getClass().getResource("/img/flip/poker_bg1.png").toExternalForm();
                break;
            case 2:
                bgUrl = getClass().getResource("/img/flip/poker_bg2.png").toExternalForm();
                break;
            case 3:
                bgUrl = getClass().getResource("/img/flip/poker_bg3.png").toExternalForm();
                break;
            default:
                bgUrl = getClass().getResource("/img/flip/poker_bg4.png").toExternalForm();
                break;
        }


        frontPane = new Pane();
        frontPane.setVisible(false);
        frontPane.setStyle("-fx-background-color: #6cf");
        Pane backPane = new Pane();
        backPane.setStyle("-fx-background-image:url('" + bgUrl + "')");
        StackPane sp = new StackPane(frontPane, backPane);
        sp.setMaxSize(72, 109);

        sp.setOnMouseClicked(mouseEvent -> {
            dir = flip == Flip.Y ? mouseEvent.getY() > sp.getWidth() / 2 : mouseEvent.getX() <= sp.getWidth() / 2;
            if (backPane.isVisible()) {
                flipAnim(frontPane, backPane, dir);
            } else {
                flipAnim(backPane, frontPane, dir);
            }

        });

        Scene scene = new Scene(new BorderPane(sp), 500, 500);
        scene.setCamera(new ParallelCamera());
        stage.setScene(scene);
        stage.setTitle("随机扑克牌");
        stage.show();
    }

    private void flipAnim(Pane showPane, Pane hidePane, boolean dir) {
        if (flipping) {
            return;
        }
        flipping = true;
        if (showPane == frontPane) {
            int x = -random.nextInt(13) * 72;
            int y = -random.nextInt(4) * 109;
            frontPane.setStyle(
                    "-fx-background-image: url('" + pokerUrl + "');" +
                            "-fx-background-position: " + x + " " + y + ";");
        }
        //设置动画的完成时间
        Duration duration = Duration.millis(500);
        //隐藏当前显示的页面
        RotateTransition hideRt = new RotateTransition(duration, hidePane);
        //沿着Y轴旋转
        hideRt.setAxis(flip == Flip.X ? Rotate.X_AXIS : Rotate.Y_AXIS);
        //从多少度开始
        hideRt.setFromAngle(0);
        //多少度结束
        hideRt.setToAngle(dir ? 90 : -90);
        hideRt.setOnFinished(event -> {
            hidePane.setVisible(false);
            showPane.setVisible(true);
        });

        //显示刚刚隐藏的页面
        RotateTransition showRt = new RotateTransition(duration, showPane);
        showRt.setAxis(flip == Flip.X ? Rotate.X_AXIS : Rotate.Y_AXIS);
        showRt.setFromAngle(dir ? -90 : 90);
        showRt.setToAngle(0);
        //设置动画播放的顺序
        SequentialTransition st = new SequentialTransition(hideRt, showRt);
        st.setOnFinished(event -> {
            //翻转完毕修改状态
            flipping = false;
        });
        st.play();
    }

    /**
     * 翻转方式
     *
     * @author Y
     * @date 2023/01/27
     */
    enum Flip {

        /**
         * x轴旋转
         */
        X,
        /**
         * y轴旋转
         */
        Y
    }
}
