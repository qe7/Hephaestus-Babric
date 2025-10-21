package io.github.qe7;

import io.github.qe7.core.bus.EventManager;
import io.github.qe7.core.feature.command.CommandManager;
import io.github.qe7.core.feature.module.ModuleManager;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.toolbox.GitUtil;
import io.github.qe7.toolbox.render.font.FontManager;
import lombok.Getter;
import lombok.Setter;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
public class HephaestusMod implements ModInitializer {

    public static HephaestusMod INSTANCE;

    private final Logger logger = LoggerFactory.getLogger("heph");

    private final String name;
    private final String version;
    private final String buildTime;

    public HephaestusMod() {
        INSTANCE = this;

        this.name = "Hephaestus";
        this.version = String.format("%s-%s", "2.0.0", GitUtil.getCommitId());
        this.buildTime = GitUtil.getCommitTime();

        ManagerFactory.register(EventManager.class, new EventManager());
        ManagerFactory.register(ModuleManager.class, new ModuleManager());
        ManagerFactory.register(CommandManager.class, new CommandManager());
        ManagerFactory.register(FontManager.class, new FontManager());
    }

    @Override
    public void onInitialize() {
        this.getLogger().info("{} {} ({})", this.getName(), this.getVersion(), this.getBuildTime());
    }
}
