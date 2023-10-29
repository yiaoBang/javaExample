package com.y.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.y.Dir;
import javafx.util.Duration;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/11 14:34
 */
public class MoveComponent extends Component {
    private static final int INIT_SPEED = 1500;
    private double speed;
    private AnimationChannel acUp, acDown, acLeft, acRight;
    private AnimatedTexture at;
    private Dir dir;
    private boolean stop;

    public MoveComponent() {
        acUp = createAc(12, 15);
        acDown = createAc(0, 3);
        acLeft = createAc(4, 7);
        acRight = createAc(8, 11);
        at = new AnimatedTexture(acDown);
        dir = Dir.DOWN;
    }

    private static AnimationChannel createAc(int startFrame, int endFrame) {
        return new AnimationChannel(
                FXGL.image("move.png"),
                4, 192 / 4, 192 / 4,
                Duration.seconds(0.75),
                startFrame, endFrame);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(at);
        entity.getBoundingBoxComponent().addHitBox(new HitBox(BoundingShape.box(192 / 4.0, 192 / 4.0)));
    }

    @Override
    public void onUpdate(double tpf) {
        if (dir == Dir.DOWN || dir == Dir.UP) {
            entity.translateX(0);
            entity.translateY(speed * tpf);
        } else {
            entity.translateY(0);
            entity.translateX(speed * tpf);
        }
        switch (dir) {
            case UP -> {
                if (at.getAnimationChannel() != acUp || stop) {
                    stop = false;
                    at.loopAnimationChannel(acUp);
                }
            }
            case DOWN -> {
                if (at.getAnimationChannel() != acDown || stop) {
                    stop = false;
                    at.loopAnimationChannel(acDown);
                }
            }
            case LEFT -> {
                if (at.getAnimationChannel() != acLeft || stop) {
                    stop = false;
                    at.loopAnimationChannel(acLeft);
                }
            }
            case RIGHT -> {
                if (at.getAnimationChannel() != acRight || stop) {
                    stop = false;
                    at.loopAnimationChannel(acRight);
                }
            }
        }
        speed = speed * 0.9;
        if (Math.abs(speed) < 1) {
            speed = 0;
            at.stop();
            stop = true;
        }
    }

    public void moveUp() {
        dir = Dir.UP;
        speed = -INIT_SPEED;
    }

    public void moveDown() {
        dir = Dir.DOWN;
        speed = INIT_SPEED;
    }

    public void moveLeft() {
        dir = Dir.LEFT;
        speed = -INIT_SPEED;
    }

    public void moveRight() {
        dir = Dir.RIGHT;
        speed = INIT_SPEED;
    }
}
