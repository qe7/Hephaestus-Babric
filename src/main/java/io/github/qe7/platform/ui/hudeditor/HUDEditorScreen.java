package io.github.qe7.platform.ui.hudeditor;

import io.github.qe7.core.bus.EventManager;
import io.github.qe7.core.bus.Listener;
import io.github.qe7.core.bus.SubscribeEvent;
import io.github.qe7.core.feature.hudelement.AbstractHUDElement;
import io.github.qe7.core.feature.hudelement.HUDElementManager;
import io.github.qe7.core.feature.module.AbstractModule;
import io.github.qe7.core.feature.module.ModuleManager;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.core.ui.GuiHephaestus;
import io.github.qe7.core.ui.component.Component;
import io.github.qe7.core.ui.component.WindowComponent;
import io.github.qe7.events.GuiMouseReleasedEvent;

import java.util.List;
import java.util.stream.Collectors;

public class HUDEditorScreen extends GuiHephaestus {

    public HUDEditorScreen() {
        ManagerFactory.get(EventManager.class).registerHandler(this);
    }

    @Override
    public void loadGui() {
        WindowComponent windowComponent = new WindowComponent("Test");

        windowComponent.setOpen(true);

        List<Component> featureComponents = ManagerFactory.get(HUDElementManager.class).getElementsAsList().stream()
                .map(AbstractHUDElement::getComponent)
                .collect(Collectors.toList());

        windowComponent.children.addAll(featureComponents);

        components.add(windowComponent);
    }

    @Override
    public void closeGui() {
        getHudEditorModule().setEnabled(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        ManagerFactory.get(HUDElementManager.class).getElementsAsList().forEach(abstractHUDElement -> abstractHUDElement.drawScreen(mouseX, mouseY, partialTicks));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        ManagerFactory.get(HUDElementManager.class).getElementsAsList().forEach(abstractHUDElement -> abstractHUDElement.mouseClicked(mouseX, mouseY, mouseButton));
    }

    @SubscribeEvent
    private final Listener<GuiMouseReleasedEvent> guiMouseReleasedEventListener = event -> {
        final int mouseX = event.getMouseX();
        final int mouseY = event.getMouseY();
        final int mouseButton = event.getMouseButton();
        components.forEach(component -> component.mouseReleased(mouseX, mouseY, mouseButton));
        ManagerFactory.get(HUDElementManager.class).getElementsAsList().forEach(abstractHUDElement -> abstractHUDElement.mouseReleased(mouseX, mouseY, mouseButton));
    };
}
