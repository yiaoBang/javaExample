package com.y.tank;

import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.y.tank.components.TankComponent;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;


/**
 * @author Y
 * @version 1.0
 * @date 2023/10/14 15:50
 */
public class TankApp extends GameApplication {

    private Entity player;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(28 * Config.CELL_SIZE + 6 * Config.CELL_SIZE);
        settings.setHeight(28 * Config.CELL_SIZE);
        settings.setTitle("Tank");
        settings.setVersion("1.0");
        settings.setAppIcon("icon.png");
        settings.setDefaultCursor(new CursorInfo("cursor.png", 0, 0));
    }

    @Override
    protected void initInput() {
        onKey(KeyCode.W, () -> {
            player.getComponent(TankComponent.class).moveUp();
            return null;
        });
        onKey(KeyCode.S, () -> {
            player.getComponent(TankComponent.class).moveDown();
            return null;
        });
        onKey(KeyCode.A, () -> {
            player.getComponent(TankComponent.class).moveLeft();
            return null;
        });
        onKey(KeyCode.D, () -> {
            player.getComponent(TankComponent.class).moveRight();
            return null;
        });
        onKey(KeyCode.J, () -> {
            player.getComponent(TankComponent.class).shoot();
            return null;
        });
    }

    @Override
    protected void initGame() {
        //1.设置游戏背景色
        getGameScene().setBackgroundColor(Color.BLACK);

        //2.指定创建游戏实体的工厂类
        getGameWorld().addEntityFactory(new TankEntityFactory());
        //加载游戏地图
        FXGL.setLevelFromMap("1.tmx");

        player = spawn("player", new SpawnData());
    }
}
