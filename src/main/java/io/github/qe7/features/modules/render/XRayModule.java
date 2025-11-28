package io.github.qe7.features.modules.render;

import io.github.qe7.core.bus.Listener;
import io.github.qe7.core.bus.SubscribeEvent;
import io.github.qe7.core.feature.module.AbstractModule;
import io.github.qe7.core.feature.module.ModuleCategory;
import io.github.qe7.events.render.RenderBlockSideEvent;
import io.github.qe7.toolbox.ChatUtil;

public final class XRayModule extends AbstractModule {

    public XRayModule() {
        super("XRay", "We all had a dream where we had this ability so we can be rich, right?", ModuleCategory.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        super.onDisable();

        mc.renderGlobal.loadRenderers();
    }

    @SubscribeEvent
    private final Listener<RenderBlockSideEvent> renderBlockSideEventListener = event -> {
        ChatUtil.addMessage("XRay blocked a block render!");
        event.setCancelled(true);
    };
}
