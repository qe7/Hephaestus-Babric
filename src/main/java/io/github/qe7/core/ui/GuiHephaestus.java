package io.github.qe7.core.ui;

import io.github.qe7.core.bus.Handler;
import io.github.qe7.core.feature.module.ModuleManager;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.features.modules.client.ClickGUIModule;
import io.github.qe7.core.ui.component.Component;
import io.github.qe7.core.ui.component.WindowComponent;
import io.github.qe7.features.modules.client.HUDEditorModule;
import io.github.qe7.toolbox.animation.Easing;
import io.github.qe7.toolbox.animation.TimeAnimation;
import io.github.qe7.toolbox.render.GuiUtil;
import lombok.Getter;
import net.minecraft.src.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GuiHephaestus extends GuiScreen implements Handler {

    @Getter
    private static final ClickGUIModule clickGUIModule = (ClickGUIModule) ManagerFactory.get(ModuleManager.class).get(ClickGUIModule.class);
    @Getter
    private static final HUDEditorModule hudEditorModule = (HUDEditorModule) ManagerFactory.get(ModuleManager.class).get(HUDEditorModule.class);

    private final TimeAnimation openAnimation = new TimeAnimation(false, 0, 250, 100, Easing.LINEAR);

    protected final List<Component> components = new ArrayList<>();
    private final Map<Component, Integer> scrollMap = new HashMap<>();

    private int scroll;

    protected GuiHephaestus() {
    }

    @Override
    public void initGui() {
        openAnimation.setState(true);
    }

    @Override
    public void onGuiClosed() {
        openAnimation.setState(false);
        closeGui();
    }

    public abstract void loadGui();

    public abstract void closeGui();

    public void loadComponents() {
        loadGui();
        for (Component component : components) {
            if (component instanceof WindowComponent) {
                scrollMap.put(component, 0);
            }
        }

        alignComponents();
    }

    public void alignComponents() {
        resetScroll();
        int componentWidth = getClickGUIModule().getWidth().getValue();
        int x = 4;
        int y = 4;
        for (Component component : components) {
            if (component instanceof WindowComponent) {
                component.setX(x);
                component.setY(y);
                component.setWidth(componentWidth);

                // TODO: Make this dynamic based off the number of components and screen width (scaled resolution)
                x += 2 + componentWidth;
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GuiUtil.OVERRIDE_MULTIPLIER = (float) openAnimation.getLinearFactor();
        GuiUtil.OVERRIDE = true;

        for (Component component : components) {
            component.setWidth(getClickGUIModule().getWidth().getValue());
            component.drawScreen(mouseX, mouseY, partialTicks);

            if (component.isMouseOver(mouseX, mouseY, component.getHeight())) {
                if (component instanceof WindowComponent) {
                    scrollComponent(component, scroll);
                }
            }
        }
        scroll = 0;

        GuiUtil.OVERRIDE = false;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
            return;
        }

        components.forEach(component -> component.keyTyped(typedChar, keyCode));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        components.forEach(component -> component.mouseClicked(mouseX, mouseY, mouseButton));
    }

    private void scrollComponent(Component component, int scroll) {
        scrollMap.computeIfPresent(component, (k, v) -> v + scroll);
        component.setY(component.getY() + scroll);
    }

    protected void resetScroll() {
        for (Map.Entry<Component, Integer> entry : scrollMap.entrySet()) {
            Component component = entry.getKey();
            int scrollValue = entry.getValue();
            component.setY(component.getY() - scrollValue);
            entry.setValue(0);
        }
    }
}
