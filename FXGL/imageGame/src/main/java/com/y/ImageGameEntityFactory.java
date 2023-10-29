package com.y;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import com.y.components.BackgroundComponent;
import com.y.components.SayHiComponent;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/11 11:11
 */
public class ImageGameEntityFactory implements EntityFactory {
    @Spawns("car")
    public Entity newCar(SpawnData data) {
        Texture carL = FXGL.texture("car.png");
        Texture carR = carL.copy();
        carR.setScaleX(-1);
        carR.setTranslateX(carL.getWidth());
        return FXGL.entityBuilder(data)
                .view(carL)
                .view(carR)
                .bbox(BoundingShape.box(carL.getWidth() * 2, carL.getHeight()))
                .build();
    }

    @Spawns("boom")
    public Entity newBoom(SpawnData data) {
        AnimationChannel an = new AnimationChannel(
                FXGL.image("explode.png"),
                Duration.seconds(0.5), 9);
        AnimatedTexture at = new AnimatedTexture(an);
        at.loop();
        return FXGL.entityBuilder(data)
                .view(at)
                .build();
    }

    @Spawns("wandou")
    public Entity newWandou(SpawnData data) {
        ArrayList<Image> imageList = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            imageList.add(FXGL.image("player/" + i + ".png"));
        }
        AnimationChannel ac = new AnimationChannel(imageList, Duration.seconds(1.1));
        AnimatedTexture at = new AnimatedTexture(ac);
        at.loop();
        return FXGL.entityBuilder(data)
                .view(at)
                .build();
    }

    @Spawns("move")
    public Entity newMove(SpawnData data) {
        AnimationChannel ac = new AnimationChannel(FXGL.image("move.png"), 4, 192 / 4, 192 / 4, Duration.seconds(0.3), 0, 6);
        AnimatedTexture at = new AnimatedTexture(ac);
        at.loop();
        return FXGL.entityBuilder(data)
                .view(at)
                .build();
    }


    @Spawns("bg")
    public Entity newBg(SpawnData data) {
        Texture texture = FXGL.texture("bg.png", 535 / 5.0, 800 / 5.0);
        Texture texture1 = texture.multiplyColor(FXGLMath.randomColor());

        return FXGL.entityBuilder(data)
                .view(texture1)
                .with(new BackgroundComponent())
                .with(new SayHiComponent())
                .neverUpdated()
                .build();
    }
}
