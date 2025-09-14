package io.github.qe7.platform.ui.api.component.special;

import io.github.qe7.core.feature.module.settings.impl.EnumSetting;
import io.github.qe7.core.feature.module.settings.impl.interfaces.IEnumSetting;
import io.github.qe7.platform.ui.api.component.ParentComponent;
import lombok.Getter;

@Getter
public class EnumComponent<E extends IEnumSetting> extends ParentComponent {

    private final EnumSetting<E> setting;

    public EnumComponent(String name, EnumSetting<E> setting) {
        super(() -> true, name);
        this.setting = setting;
    }

    @Override
    protected void renderComponent() {
        getTheme().renderEnumComponent(this);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isMouseOver(mouseX, mouseY, getHeaderHeight())) {
            if (mouseButton == 0) {
                setting.cycleForward();
            } else if (mouseButton == 1) {
                setting.cycleBackward();
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

}
