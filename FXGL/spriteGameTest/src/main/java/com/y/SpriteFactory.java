package com.y;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.KeepOnScreenComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.y.component.MoveComponent;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/11 14:32
 */
public class SpriteFactory implements EntityFactory {
    @Spawns("move")
    public Entity newMove(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new KeepOnScreenComponent())
                .with(new MoveComponent())
                .build();
    }
}
