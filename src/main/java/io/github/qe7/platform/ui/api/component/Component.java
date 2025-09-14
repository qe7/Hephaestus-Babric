package io.github.qe7.platform.ui.api.component;

public interface Component {

    String getName();

    boolean isVisible();

    void drawScreen(int mouseX, int mouseY, float partialTicks);

    void keyTyped(char typedChar, int keyCode);

    void mouseClicked(int mouseX, int mouseY, int mouseButton);

    void mouseReleased(int mouseX, int mouseY, int state);

    float getX();

    float getY();

    float getWidth();

    float getHeight();

    void setX(float x);

    void setY(float y);

    void setWidth(float width);

    void setHeight(float height);

    default boolean isMouseOver(double mouseX, double mouseY, double height) {
        return mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY > getY() && mouseY < getY() + height;
    }
}
