package com.y;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.cutscene.Cutscene;
import com.almasb.fxgl.dsl.FXGL;
import javafx.util.Duration;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/9 17:09
 */
public class ChartApp extends GameApplication {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(535);
        settings.setHeight(800);
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new ChatFactory());
        spawn("bg");

        runOnce(() -> {
            List<String> lines = getAssetLoader().loadText("chat.txt");
            Cutscene cutscene = new Cutscene(lines);
            getCutsceneService().startCutscene(cutscene);
            return null;
        }, Duration.ONE);
    }
}
