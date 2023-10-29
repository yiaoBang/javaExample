package com.y.test;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

import java.util.Map;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/8 12:37
 */
public class TestMethodApp extends GameApplication {
    public static int num = 1;

    public TestMethodApp() {
        System.out.println("构造器===> " + (num++) + "线程名称: " + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        System.out.println("Main方法===> " + (num++) + "线程名称: " + Thread.currentThread().getName());
        launch(args);
    }

    /**
     * 初始化游戏设置,比如宽高,版本,图标,菜单等
     *
     * @param settings
     */
    @Override
    protected void initSettings(GameSettings settings) {
        //设置主菜单可见
        settings.setMainMenuEnabled(true);
        System.out.println("initSettings===> " + (num++) + "线程名称: " + Thread.currentThread().getName());
    }

    /**
     * 游戏预处理
     */
    @Override
    protected void onPreInit() {
        System.out.println("onPreInit===> " + (num++) + "线程名称: " + Thread.currentThread().getName());
    }

    /**
     * 设置输入(鼠标,键盘)
     */
    @Override
    protected void initInput() {
        System.out.println("initInput===> " + (num++) + "线程名称: " + Thread.currentThread().getName());
    }

    /**
     * 设置一些游戏的变量 可以方便的在其他类进行访问
     *
     * @param vars
     */
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        System.out.println("initGameVars===> " + (num++) + "线程名称: " + Thread.currentThread().getName());
    }

    /**
     * 初始化游戏,比如设置音量
     */
    @Override
    protected void initGame() {
        System.out.println("initGame===> " + (num++) + "线程名称: " + Thread.currentThread().getName());
    }

    /**
     * 游戏物理事件,比如碰撞
     */
    @Override
    protected void initPhysics() {
        System.out.println("initPhysics===> " + (num++) + "线程名称: " + Thread.currentThread().getName());
    }

    /**
     * 初始化界面上的组件
     */
    @Override
    protected void initUI() {
        System.out.println("initUI===> " + (num++) + "线程名称: " + Thread.currentThread().getName());
    }

    /**
     * 游戏开始后每一帧都调用
     *
     * @param tpf TPF
     */
    @Override
    protected void onUpdate(double tpf) {
        // System.out.println("onUpdate===> "+(num++)+"线程名称: "+Thread.currentThread().getName());
    }
}
