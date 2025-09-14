package io.github.qe7.platform.ui.api.component;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Setter
@Getter
public class ParentComponent extends AbstractComponent {

    public List<Component> children = new ArrayList<>();

    protected boolean open = false;

    public ParentComponent(Supplier<Boolean> visibilitySupplier, String name) {
        super(visibilitySupplier, name);
    }

    public ParentComponent(String name) {
        super(() -> true, name);
    }

    public ParentComponent() {
        super();
    }

    public float getHeaderHeight() {
        return getTheme().getDefaultHeaderHeight();
    }

    public float getFooterHeight() {
        return getTheme().getDefaultFooterHeight();
    }

    public float getBorderWidth() {
        return getTheme().getDefaultBorderWidth();
    }

    protected void renderComponent() {
        getTheme().renderParentComponent(this);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        renderComponent();

        GL11.glPushMatrix();
//        ScissorUtil.prepareScissorBox(getX(), getY(), getX() + getWidth(), getY() + getHeight());
//        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        setHeight(getHeaderHeight());

        if (isOpen()) {
            setHeight(getHeight() + getFooterHeight());

            for (Component child : children) {
                if (child.isVisible()) {
                    child.setX(getX() + getBorderWidth());
                    child.setWidth(getWidth() - getBorderWidth() * 2);
                    child.setY(getY() + getHeight());
                    child.drawScreen(mouseX, mouseY, partialTicks);

                    float heightToAdd = child.getHeight();

                    setHeight(getHeight() + heightToAdd);
                }
            }

            setHeight(getHeight() + getFooterHeight());
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (open) {
            children.forEach(child -> child.keyTyped(typedChar, keyCode));
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isMouseOver(mouseX, mouseY, getHeaderHeight())) {
            if (mouseButton == 1) {
                if (!children.isEmpty()) {
                    open = !open;
                }
            }
        }

        if (open) {
            children.forEach(child -> child.mouseClicked(mouseX, mouseY, mouseButton));
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (open) {
            children.forEach(child -> child.mouseReleased(mouseX, mouseY, state));
        }
    }

}
