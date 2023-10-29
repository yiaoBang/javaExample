package com.y.test;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/8 13:12
 */
public class TestEntityFactory implements EntityFactory {
    @Spawns("rect")
    public Entity newRect(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view(new Rectangle(60, 60, Color.ORANGE))
                .build();
    }
}
