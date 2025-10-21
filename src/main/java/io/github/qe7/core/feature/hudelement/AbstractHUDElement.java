package io.github.qe7.core.feature.hudelement;

import io.github.qe7.core.bus.Handler;
import io.github.qe7.core.common.Displayable;
import io.github.qe7.core.common.Global;
import io.github.qe7.core.common.Nameable;
import io.github.qe7.core.common.Toggleable;
import io.github.qe7.core.feature.settings.AbstractSetting;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.core.ui.DragHandler;
import io.github.qe7.core.ui.component.Component;
import io.github.qe7.core.ui.component.FeatureComponent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractHUDElement implements Global, Nameable, Toggleable, Handler, Displayable {

    private final String name;

    protected int x, y, width, height;

    private boolean enabled;

    private DragHandler dragHandler;

    public AbstractHUDElement(String name) {
        this.name = name;

        this.x = 10;
        this.y = 10;
        this.width = 100;
        this.height = 20;
    }

    @Override
    public boolean listening() {
        return this.isEnabled();
    }

    @Override
    public void toggle() {
        this.setEnabled(!this.isEnabled());
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (this.dragHandler == null) {
            this.dragHandler = new DragHandler(this.x, this.y);
        }

        this.dragHandler.handleRender(mouseX, mouseY);
        if (this.dragHandler.isDragging()) {
            this.x = (int) this.dragHandler.getX();
            this.y = (int) this.dragHandler.getY();
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height) {
            this.dragHandler.handleMouseClicked(mouseX, mouseY, mouseButton, true);
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.dragHandler.handleMouseClicked(mouseX, mouseY, state, false);
    }

    @Override
    public Component getComponent() {
        FeatureComponent component = new FeatureComponent(getName(), this::isEnabled, this::setEnabled);

        for (AbstractSetting<?> abstractSetting : ManagerFactory.get(HUDElementManager.class).getSettingsByElement(this)) {
            if (abstractSetting.getComponent() != null) {
                component.children.add(abstractSetting.getComponent());
            }
        }

        return component;
    }
}
