package com.Y;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 叉车
 *
 * @author Y
 * @date 2023/02/02
 */
public class Forklif extends ImageView {
    /**
     * 叉车移动方向
     */
    private Direction direction;

    private long lastTime;

    public Forklif(Image image) {
        super(image);
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}