package io.github.qe7.features.hudelements;

import io.github.qe7.core.bus.Listener;
import io.github.qe7.core.bus.SubscribeEvent;
import io.github.qe7.core.feature.hudelement.AbstractHUDElement;
import io.github.qe7.core.feature.settings.impl.BooleanSetting;
import io.github.qe7.events.ScreenEvent;
import io.github.qe7.toolbox.render.GuiUtil;
import net.minecraft.src.FontRenderer;

public final class TestHUDElement extends AbstractHUDElement {

    private final BooleanSetting test = new BooleanSetting("Test Setting", true);

    public TestHUDElement() {
        super("Test HUDElement");
    }

    @SubscribeEvent
    private final Listener<ScreenEvent> screenEventListener = event -> {
        final FontRenderer fontRenderer = mc.fontRenderer;

        this.setHeight(10 + 4);
        this.setWidth(fontRenderer.getStringWidth("This is a test HUD Element") + 4);

        GuiUtil.drawRect(x, y, x + width, y + height, 0x90000000);

        fontRenderer.drawStringWithShadow("This is a test HUD Element", x + 2, y + 2, 0xFFFFFF);
    };
}
