package io.github.qe7.platform.ui;

import io.github.qe7.core.bus.EventManager;
import io.github.qe7.core.bus.Listener;
import io.github.qe7.core.bus.SubscribeEvent;
import io.github.qe7.core.feature.module.AbstractModule;
import io.github.qe7.core.feature.module.ModuleCategory;
import io.github.qe7.core.feature.module.ModuleManager;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.events.GuiMouseReleasedEvent;
import io.github.qe7.core.ui.GuiHephaestus;
import io.github.qe7.core.ui.component.Component;
import io.github.qe7.core.ui.component.WindowComponent;

import java.util.List;
import java.util.stream.Collectors;

public class ClickGuiScreen extends GuiHephaestus {

    public ClickGuiScreen() {
        ManagerFactory.get(EventManager.class).registerHandler(this);
    }

    @Override
    public void loadGui() {
        for (ModuleCategory category : ModuleCategory.values()) {
            WindowComponent windowComponent = new WindowComponent(category.getName());

            windowComponent.setOpen(true);

            List<Component> featureComponents = ManagerFactory.get(ModuleManager.class).getModulesByCategory(category).stream()
                    .map(AbstractModule::getComponent)
                    .collect(Collectors.toList());

            windowComponent.children.addAll(featureComponents);

            components.add(windowComponent);
        }
    }

    @Override
    public void closeGui() {
        getClickGUIModule().setEnabled(false);
    }

    @SubscribeEvent
    private final Listener<GuiMouseReleasedEvent> guiMouseReleasedEventListener = event -> {
        final int mouseX = event.getMouseX();
        final int mouseY = event.getMouseY();
        final int mouseButton = event.getMouseButton();
        components.forEach(component -> component.mouseReleased(mouseX, mouseY, mouseButton));
    };
}
