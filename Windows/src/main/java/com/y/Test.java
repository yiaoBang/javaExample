package com.y;

import com.sun.jna.platform.win32.User32;

/**
 * @author Y
 * @version 1.0
 * @date 2023/11/2 15:43
 */
public class Test {
    public static final User32 USER_32 = User32.INSTANCE;

    public static void main(String[] args) throws InterruptedException {
        String title = "激光系统输出调试";
        String add;
        while (true) {
            add = checkWindow(title)?"存在":"不存在";
            System.out.println("窗口: "+title +"  "+ add);
            Thread.sleep(5000);
        }
    }

    public static boolean checkWindow(String title) {
        return USER_32.FindWindow(null, title) != null;
    }
}
