package com.y.test;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/8 13:12
 */
public class TestEntityApp extends GameApplication {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {

    }

    @Override
    protected void initGame() {
        //        Entity entity = FXGL.entityBuilder().build();
//        FXGL.getGameWorld().addEntities(entity);

//        FXGL.entityBuilder(new SpawnData(100,50)).buildAndAttach();

        FXGL.getGameWorld().addEntityFactory(new TestEntityFactory());
        FXGL.getGameWorld().spawn("rect");
    }
}
