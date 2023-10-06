package com.y;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Test extends Application {
    SystemTray systemTray;
    TrayIcon trayIcon;

    public static void main(String[] args) {
        launch(args);
    }

    private void initTrayIcon(Stage mainStage) {
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
                            mainStage.requestFocus();
                            //获得当前屏幕缩放比例
                            double scaleX = Screen.getPrimary().getOutputScaleX();
                            double scaleY = Screen.getPrimary().getOutputScaleY();
                            contextMenu.show(mainStage, e.getXOnScreen() / scaleX + 8, e.getYOnScreen() / scaleY - 90);
                        });

                    }
                }
            });
        });

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
        initTrayIcon(stage);
    }
}
