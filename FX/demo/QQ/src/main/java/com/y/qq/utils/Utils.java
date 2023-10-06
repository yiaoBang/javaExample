package com.y.qq.utils;

public class Utils {
    private Utils() {

    }

    public static String getResourceUrl(String path) {
        return Utils.class.getResource(path).toExternalForm();
    }
}
