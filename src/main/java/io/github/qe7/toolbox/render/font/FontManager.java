package io.github.qe7.toolbox.render.font;

import io.github.qe7.core.manager.AbstractManager;
import io.github.qe7.toolbox.render.font.renderer.TTFRenderer;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public final class FontManager extends AbstractManager<String, TTFRenderer> {

    public TTFRenderer getFontRenderer(FontType fontType, float size) {
        String key = getFontKey(fontType, size);
        if (!map.containsKey(key)) {
            map.put(key, createRenderer(fontType.getFontPath(), size));
        }
        return map.get(key);
    }

    private String getFontKey(FontType fontType, float size) {
        return fontType.getFontPath() + ":" + size;
    }

    private TTFRenderer createRenderer(String path, float size) {
        try {
            return new TTFRenderer(Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(FontManager.class.getResourceAsStream("/assets/heph/font/" + path + ".ttf"))).deriveFont(Font.PLAIN, size), true);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}