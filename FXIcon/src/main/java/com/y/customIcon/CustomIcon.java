package com.y.customIcon;

import org.kordamp.ikonli.Ikon;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/6 11:55
 */
public enum CustomIcon implements Ikon {
    /**
     * 指纹
     */
    FINGERPRINT("custom-fingerprint", '\ue900'),
    /**
     * 文件夹
     */
    FOLDER("custom-folder", '\ue901'),
    /**
     * 电源
     */
    POWER("custom-power", '\ue902'),
    /**
     * 打印
     */
    PRINT("custom-print", '\ue903'),
    /**
     * 设置
     */
    SETTINGS("custom-settings", '\ue904');
    private final String description;
    private final int code;

    CustomIcon(String description, int code) {
        this.description = description;
        this.code = code;
    }

    /**
     * 按描述查找图标
     *
     * @param description 描述
     * @return {@code CustomIcon}
     */
    public static CustomIcon findIkonByDesc(String description) {
        for (CustomIcon icon : values()) {
            if (icon.description.equals(description)) {
                return icon;
            }
        }
        throw new IllegalArgumentException("找不到对应的图标:" + description);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getCode() {
        return code;
    }
}
