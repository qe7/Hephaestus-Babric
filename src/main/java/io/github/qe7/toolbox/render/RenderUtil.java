package io.github.qe7.toolbox.render;

import io.github.qe7.core.common.Globals;
import lombok.experimental.UtilityClass;

import java.awt.*;

@UtilityClass
public final class RenderUtil implements Globals {

    public static void drawStringOutlined(String text, int x, int y, Color color) {
        String replaced = text.replaceAll("(?i)§[\\da-f]", "");
        mc.fontRenderer.drawString(replaced, x - 1, y, darker(color, 0.3f).getRGB());
        mc.fontRenderer.drawString(replaced, x + 1, y, darker(color, 0.3f).getRGB());
        mc.fontRenderer.drawString(replaced, x, y - 1, darker(color, 0.3f).getRGB());
        mc.fontRenderer.drawString(replaced, x, y + 1, darker(color, 0.3f).getRGB());
        mc.fontRenderer.drawString(text, x, y, color.getRGB());
    }

    public static Color darker(Color color, float factor) {
        return scale(color, factor, color.getAlpha());
    }

    public static Color scale(Color color, float scaleFactor, int alpha) {
        return new Color(Math.min(Math.max((int) ((float) color.getRed() * scaleFactor), 0), 255), Math.min(Math.max((int) ((float) color.getGreen() * scaleFactor), 0), 255), Math.min(Math.max((int) ((float) color.getBlue() * scaleFactor), 0), 255), alpha);
    }
}
