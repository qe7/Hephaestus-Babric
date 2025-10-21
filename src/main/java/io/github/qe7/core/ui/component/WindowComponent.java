package io.github.qe7.core.ui.component;

import io.github.qe7.core.ui.DragHandler;

public class WindowComponent extends ParentComponent {

    private DragHandler dragHandler;

    public WindowComponent(String name) {
        super(() -> true, name);
    }

    @Override
    public float getHeaderHeight() {
        return getTheme().getWindowHeaderHeight();
    }

    @Override
    public float getFooterHeight() {
        return getTheme().getWindowFooterHeight();
    }

    @Override
    public float getBorderWidth() {
        return getTheme().getWindowBorderWidth();
    }

    @Override
    protected void renderComponent() {
        getTheme().renderWindowComponent(this);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (dragHandler == null)
        {
            this.dragHandler = new DragHandler(getX(),
                    getY());
        }

        dragHandler.handleRender(mouseX,
                mouseY);
        if (dragHandler.isDragging())
        {
            setX(dragHandler.getX());
            setY(dragHandler.getY());
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (dragHandler == null)
        {
            this.dragHandler = new DragHandler(getX(),
                    getY());
        }

        dragHandler.handleMouseClicked(
                (float) mouseX,
                (float) mouseY,
                mouseButton,
                isMouseOver(mouseX, mouseY, getHeaderHeight()));
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);

        if (dragHandler == null)
        {
            this.dragHandler = new DragHandler(getX(),
                    getY());
        }

        dragHandler.setDragging(false);
    }
}
