package io.github.qe7.toolbox.render;

import io.github.qe7.core.common.Globals;
import lombok.experimental.UtilityClass;
import org.lwjgl.opengl.GL11;

@UtilityClass
public final class ScissorUtil implements Globals {

    public static void prepareScissorBox(float startX, float startY, float endX, float endY) {
        int displayWidth = mc.displayWidth;
        int displayHeight = mc.displayHeight;
        int scaledWidth = mc.currentScreen.width;
        int scaledHeight = mc.currentScreen.height;

        // Convert GUI coords -> real pixel coords
        float scaleX = (float) displayWidth / (float) scaledWidth;
        float scaleY = (float) displayHeight / (float) scaledHeight;

        float scissorX = startX * scaleX;
        float scissorY = (scaledHeight - endY) * scaleY;
        float scissorWidth = (endX - startX) * scaleX;
        float scissorHeight = (endY - startY) * scaleY;

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(
                (int) scissorX,
                (int) scissorY,
                (int) scissorWidth,
                (int) scissorHeight
        );
    }

    public static void disableScissor() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
