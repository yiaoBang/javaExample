package com.y.qq;

import com.y.qq.utils.Utils;
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static com.y.qq.utils.Utils.getResourceUrl;

/**
 * qqlogin应用
 *
 * @author Y
 * @date 2023/01/28
 */
public class QQLoginApp extends Application {
    private final ArrayList<Image> imgList = new ArrayList<>(260);
    SystemTray systemTray;
    TrayIcon trayIcon;
    /**
     * 纪录偏移量
     */
    private double offsetX;
    private double offsetY;
    private int index;
    private Stage mainStage;
    private Stage stage;
    //记录当前状态是否正在翻转
    private boolean flipping;

    @Override
    public void start(Stage stage) throws Exception {
        //不默认推出
        Platform.setImplicitExit(false);
        for (int i = 13; i < 177; i++) {
            imgList.add(new Image(getResourceUrl("/img/loginframe/login" + i + ".png")));
        }
        this.stage = stage;
        Scene scene1 = new Scene(new BorderPane(), 1, 1);
        scene1.getStylesheets().add(getResourceUrl("/css/qqtrayicon.css"));
        stage.setScene(scene1);
        stage.initStyle(StageStyle.UTILITY);
        stage.setX(Double.MAX_VALUE);
        stage.show();
        //透明
        mainStage = new Stage(StageStyle.TRANSPARENT);
        //设置拥有者
        mainStage.initOwner(stage);
        //1登陆界面

        Pane longinPane = new Pane();
        ImageView iv = new ImageView(Utils.getResourceUrl("/img/login-pane.png"));
        longinPane.getChildren().add(iv);
        Button settingBtn = new Button();
        settingBtn.setGraphic(new ImageView(Utils.getResourceUrl("/img/setting.png")));
        settingBtn.getStyleClass().add("btn");
        settingBtn.setLayoutX(338);
        settingBtn.setLayoutY(0);

        //添加阴影
        DropShadow shadow = new DropShadow(8, new Color(0.0, 0.0, 0.0, 0.69));
        //图片
        ImageView topImgV = new ImageView();
        longinPane.getChildren().add(topImgV);
        longinPane.getChildren().add(settingBtn);

        //最小化按钮
        Button miniBtn = new Button();
        miniBtn.setGraphic(new ImageView(Utils.getResourceUrl("/img/mini.png")));
        miniBtn.getStyleClass().add("btn");
        miniBtn.setLayoutX(338 + 30);
        miniBtn.setLayoutY(0);
        miniBtn.setOnAction(e -> {
            //隐藏窗口
            mainStage.hide();
        });
        longinPane.getChildren().add(miniBtn);


        //关闭按钮
        Button exitBtn = new Button();
        exitBtn.setGraphic(new ImageView(Utils.getResourceUrl("/img/close.png")));
        exitBtn.getStyleClass().add("btn");
        exitBtn.getStyleClass().add("close-btn");
        exitBtn.setLayoutX(338 + 30 + 30);
        exitBtn.setLayoutY(0);
        exitBtn.setOnAction(e -> {
            Platform.exit();
            if (systemTray != null && trayIcon != null) {
                systemTray.remove(trayIcon);
            }
        });
        longinPane.getChildren().add(exitBtn);


        longinPane.setEffect(shadow);

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            if (index == imgList.size()) {
                index = 0;
            }
            topImgV.setImage(imgList.get(index++));
        }));
        //无限执行
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();

        //2设置界面
        Pane settingPane = new Pane();
        ImageView iv2 = new ImageView(Utils.getResourceUrl("/img/setting-pane.png"));
        settingPane.getChildren().add(iv2);
        settingPane.setVisible(false);
        Button cancelBtn = new Button("取消");
        cancelBtn.getStyleClass().add("cancel-btn");
        settingPane.getChildren().add(cancelBtn);
        cancelBtn.setLayoutX(335);
        cancelBtn.setLayoutY(302);
        settingPane.setEffect(shadow);

        StackPane contentPane = new StackPane(settingPane, longinPane);


        BorderPane root = new BorderPane(contentPane);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, null);
        scene.setCamera(new ParallelCamera());
        scene.getStylesheets().add(getResourceUrl("/css/qq.css"));
        mainStage.initStyle(StageStyle.TRANSPARENT);
        mainStage.setScene(scene);
        mainStage.show();


        settingBtn.setOnAction(event -> flipAnim(settingPane, longinPane, true));
        cancelBtn.setOnAction(event -> flipAnim(longinPane, settingPane, false));


        //设置拖动界面
        scene.setOnMousePressed(event -> {
            offsetX = event.getSceneX();
            offsetY = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            //翻转时禁止拖动
            if (flipping) {
                return;
            }
            mainStage.setX(event.getScreenX() - offsetX);
            mainStage.setY(event.getScreenY() - offsetY);
        });

        initTrayIcon();
    }

    private void initTrayIcon() {
        MenuItem openMenu = new MenuItem("", new Text("打开主面板"));
        openMenu.setOnAction(e -> {
            if (mainStage != null) {
                mainStage.show();
                mainStage.toFront();
            }
        });
        MenuItem exitMenu = new MenuItem("", new Text("退出"));
        exitMenu.setOnAction(e -> {
            Platform.exit();
            if (systemTray != null && trayIcon != null) {
                systemTray.remove(trayIcon);
            }
        });
        ContextMenu contextMenu = new ContextMenu(openMenu, new SeparatorMenuItem(), exitMenu);
        SwingUtilities.invokeLater(() -> {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            //如果系统不支持任务栏就退出
            if (!SystemTray.isSupported()) {
                Platform.exit();
            }
            systemTray = SystemTray.getSystemTray();
            trayIcon = new TrayIcon(toolkit.getImage(getClass().getResource("/img/qq.png")),
                    "QQ");
            try {
                systemTray.add(trayIcon);
            } catch (AWTException e) {
                throw new RuntimeException(e);
            }
            trayIcon.addActionListener(e -> Platform.runLater(() -> {
                if (mainStage != null) {
                    mainStage.show();
                    mainStage.toFront();
                }
            }));
            trayIcon.addMouseListener(new MouseAdapter() {
                //释放触发
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        Platform.runLater(() -> {
                            //获得焦点
                            stage.requestFocus();
                            //获得当前屏幕缩放比例
                            double scaleX = Screen.getPrimary().getOutputScaleX();
                            double scaleY = Screen.getPrimary().getOutputScaleY();
                            contextMenu.show(stage, e.getXOnScreen() / scaleX + 8, e.getYOnScreen() / scaleY - 90);
                        });

                    }
                }
            });
        });

    }

    /**
     * 翻转
     *
     * @param showPane 显示面板
     * @param hidePane 隐藏面板
     * @param dir      dir
     */
    private void flipAnim(Pane showPane, Pane hidePane, boolean dir) {
        if (flipping) {
            return;
        }
        flipping = true;

        //设置动画的完成时间
        Duration duration = Duration.millis(500);
        //隐藏当前显示的页面
        RotateTransition hideRt = new RotateTransition(duration, hidePane);
        //沿着Y轴旋转
        hideRt.setAxis(Rotate.Y_AXIS);
        //从多少度开始
        hideRt.setFromAngle(0);
        //多少度结束
        hideRt.setToAngle(dir ? 90 : -90);
        hideRt.setOnFinished(event -> {
            hidePane.setVisible(false);
            showPane.setVisible(true);
        });
        ScaleTransition hidest = new ScaleTransition(duration, hidePane);
        hidest.setFromX(1.0);
        hidest.setToX(0.6);
        hidest.setFromY(1.0);
        hidest.setToY(0.6);
        //并行动画
        ParallelTransition hideAnim = new ParallelTransition(hideRt, hidest);


        //显示刚刚隐藏的页面
        RotateTransition showRt = new RotateTransition(duration, showPane);
        showRt.setAxis(Rotate.Y_AXIS);
        showRt.setFromAngle(dir ? -90 : 90);
        showRt.setToAngle(0);

        ScaleTransition showSt = new ScaleTransition(duration, showPane);
        showSt.setFromX(0.6);
        showSt.setToX(1.0);
        showSt.setFromY(0.6);
        showSt.setToY(1.0);
        //并行动画
        ParallelTransition showAnim = new ParallelTransition(showRt, showSt);


        //设置动画播放的顺序
        SequentialTransition st = new SequentialTransition(hideAnim, showAnim);
        st.setOnFinished(event -> {
            //翻转完毕修改状态
            flipping = false;
        });
        st.play();
    }
}
