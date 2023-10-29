package com.y;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.TypeComponent;
import javafx.scene.paint.Color;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/10 16:50
 */
public class GameApp extends GameApplication {
    //FXGL : Entity Component System ECS
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {

    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new GameEntityFactory());
        Entity e1 = spawn("rect",
                new SpawnData(0, 0)
                        .put("w", 100)
                        .put("h", 100)
                        .put("color", Color.ORANGE));
        Entity e2 = spawn("rect",
                new SpawnData(100, 100)
                        .put("w", 100)
                        .put("h", 100)
                        .put("color", Color.BLUEVIOLET));
        //计算的是entity坐标的距离
        System.out.println("distance: " + e1.distance(e2));
        //计算的是实体bbox之间的距离,如果有交集,那么距离就是0
        System.out.println("distanceBBox: " + e1.distanceBBox(e2));

        Entity e3 = getGameWorld().create("rect",
                new SpawnData(100, 100)
                        .put("w", 100)
                        .put("h", 100)
                        .put("color", Color.BLUEVIOLET));

        e3.setOnActive(() -> System.out.println("E3活跃"));
        e3.setOnNotActive(() -> System.out.println("E3不活跃"));

        System.out.println("E3被创建: " + e3.isActive());

        getGameWorld().addEntities(e3);
        System.out.println("E3被添加: " + e3.isActive());

        e3.removeFromWorld();
        System.out.println("E3被移除: " + e3.isActive());


        e1.getComponents().forEach(System.out::println);
        //核心组件不能移除, 已经添加的组件也不能重复添加
//        e1.getTypeComponent(); //指定类型
//        e1.getBoundingBoxComponent(); //指定实体的大小
//        e1.getViewComponent(); //指定实体的外观
//        e1.getTransformComponent(); //指定实体的位置

        boolean b = e1.hasComponent(TypeComponent.class);
        System.out.println(b ? "有Type组件" : "没有Type组件");


    }
}
