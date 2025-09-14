package io.github.qe7.toolbox;

import lombok.experimental.UtilityClass;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

@UtilityClass
public final class GuiUtil {

    public static void drawRect(float left, float top, float right, float bottom, int color) {
        float var6;
        if (left < right) {
            var6 = left;
            left = right;
            right = var6;
        }

        if (top < bottom) {
            var6 = top;
            top = bottom;
            bottom = var6;
        }

        float alpha = (float)(color >> 24 & 255) / 255.0F;
        float red = (float)(color >> 16 & 255) / 255.0F;
        float green = (float)(color >> 8 & 255) / 255.0F;
        float blue = (float)(color & 255) / 255.0F;
        Tessellator var10 = Tessellator.instance;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(red, green, blue, alpha);
        var10.startDrawingQuads();
        var10.addVertex(left, bottom, 0.0);
        var10.addVertex(right, bottom, 0.0);
        var10.addVertex(right, top, 0.0);
        var10.addVertex(left, top, 0.0);
        var10.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
}
