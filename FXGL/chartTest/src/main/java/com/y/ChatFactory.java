package com.y;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/9 17:11
 */
public class ChatFactory implements EntityFactory {
    @Spawns("bg")
    public Entity newBg(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("bg.png")
                .build();
    }
}
