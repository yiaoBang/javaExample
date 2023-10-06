package com.y.customIcon;

import org.kordamp.ikonli.AbstractIkonHandler;
import org.kordamp.ikonli.Ikon;

import java.io.InputStream;
import java.net.URL;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/6 11:59
 */
public class CustomIconHandler extends AbstractIkonHandler {
    @Override
    public boolean supports(String description) {
        return description != null && description.startsWith("custom-");
    }

    @Override
    public Ikon resolve(String description) {
        return CustomIcon.findIkonByDesc(description);
    }

    @Override
    public URL getFontResource() {
        return CustomIconHandler.class.getResource("MyCustomIcon-v1.0/fonts/MyCustomIcon.ttf");
    }

    @Override
    public InputStream getFontResourceAsStream() {
        return CustomIconHandler.class.getResourceAsStream("MyCustomIcon-v1.0/fonts/MyCustomIcon.ttf");
    }

    @Override
    public String getFontFamily() {
        return "MyCustomIcon";
    }
}
