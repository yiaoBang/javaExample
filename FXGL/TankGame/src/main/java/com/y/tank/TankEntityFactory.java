package com.y.tank;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.y.tank.components.TankComponent;
import javafx.util.Duration;

import static com.y.tank.GameType.*;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/14 16:29
 */
public class TankEntityFactory implements EntityFactory {
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(PLAYER)
                .viewWithBBox("tank.png")
                .with(new TankComponent())
                //添加可碰撞组件 true可碰撞 false不可碰撞  默认为false
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("brick")
    public Entity newBrick(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BRICK)
                .viewWithBBox("map/brick.png")
                //添加可碰撞组件(简写)
                .collidable()
                //不更新
                .neverUpdated()
                .build();
    }

    @Spawns("greens")
    public Entity newGreens(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GREENS)
                .viewWithBBox("map/greens.png")
                .collidable()
                //指定层级(图层)  数值越高越接近最上级
                .zIndex(1000)
                .neverUpdated()
                .build();
    }

    @Spawns("sea")
    public Entity newSea(SpawnData data) {
        AnimationChannel ac = new AnimationChannel(FXGL.image("map/sea_anim.png"), Duration.seconds(1), 2);
        AnimatedTexture at = new AnimatedTexture(ac);
        return FXGL.entityBuilder(data)
                .type(SEA)
                .viewWithBBox(at.loop())
                .collidable()
                .build();
    }

    @Spawns("snow")
    public Entity newSnow(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(SNOW)
                .viewWithBBox("map/snow.png")
                .collidable()
                .neverUpdated()
                .build();
    }

    @Spawns("stone")
    public Entity newStone(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(STONE)
                .viewWithBBox("map/stone.png")
                .collidable()
                .neverUpdated()
                .build();
    }
}
