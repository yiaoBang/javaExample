package com.y.tank.components;

import com.almasb.fxgl.entity.component.Component;
import com.y.tank.Config;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/14 16:37
 */
public class TankComponent extends Component {
    private boolean moveing;
    private double distance;

    /**
     * 游戏运行时,每一帧都会调用
     *
     * @param tpf TPF 当前帧所用的时间,一般来说 一秒有60帧
     *            移动的距离 = 时间 * 速度
     */
    @Override
    public void onUpdate(double tpf) {
        moveing = false;
        distance = tpf * Config.TANK_MOVE_SPEED;
    }

    public void moveUp() {
        if (moveing) {
            return;
        }
        moveing = true;
        entity.setRotation(270);
        entity.translateY(-distance);
    }

    public void moveDown() {
        if (moveing) {
            return;
        }
        moveing = true;
        entity.setRotation(90);
        entity.translateY(distance);
    }

    public void moveLeft() {
        if (moveing) {
            return;
        }
        moveing = true;
        entity.setRotation(180);
        entity.translateX(-distance);
    }

    public void moveRight() {
        if (moveing) {
            return;
        }
        moveing = true;
        entity.setRotation(0);
        entity.translateX(distance);
    }

    public void shoot() {

    }
}
