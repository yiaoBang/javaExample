package com.y;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.geometry.Rectangle2D;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/11 11:09
 */
public class GameApp extends GameApplication {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {

    }

    /**
     * 预加载
     */
    @Override
    protected void onPreInit() {

    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new ImageGameEntityFactory());
        spawn("car");
        spawn("boom", new SpawnData(100, 100));
        spawn("wandou", new SpawnData(230, 100));
        spawn("move", new SpawnData(420, 100));

//        for (int i = 0; i < 5; i++) {
//            spawn("bg", FXGLMath.randomPoint(new Rectangle2D(0,0,600,500)));
//        }
        Entity bg = spawn("bg", FXGLMath.randomPoint(new Rectangle2D(0, 0, 600, 500)));


    }
}
