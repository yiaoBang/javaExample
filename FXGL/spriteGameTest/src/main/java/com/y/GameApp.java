package com.y;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.y.component.MoveComponent;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/11 14:31
 */
public class GameApp extends GameApplication {

    private MoveComponent component;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {

    }

    @Override
    protected void initInput() {
        onKey(KeyCode.W, () -> {
            component.moveUp();
            return null;
        });
        onKey(KeyCode.D, () -> {
            component.moveRight();
            return null;
        });
        onKey(KeyCode.S, () -> {
            component.moveDown();
            return null;
        });
        onKey(KeyCode.A, () -> {
            component.moveLeft();
            return null;
        });
    }

    @Override
    protected void initGame() {
        getGameScene().setBackgroundColor(FXGLMath.randomColor());
        getGameWorld().addEntityFactory(new SpriteFactory());
        Entity move = spawn("move");
        component = move.getComponent(MoveComponent.class);


    }
}
