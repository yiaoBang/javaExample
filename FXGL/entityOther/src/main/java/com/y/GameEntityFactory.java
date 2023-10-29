package com.y;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.scene.shape.Rectangle;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/10 16:51
 */
public class GameEntityFactory implements EntityFactory {
    @Spawns("rect")
    public Entity newRect(SpawnData data) {
        return FXGL.entityBuilder(data)
                .viewWithBBox(new Rectangle(data.<Integer>get("w"), data.<Integer>get("h"), data.get("color")))
                .build();
    }
}
