package io.github.qe7.core.feature.command;

import io.github.qe7.core.bus.EventManager;
import io.github.qe7.core.bus.Handler;
import io.github.qe7.core.bus.Listener;
import io.github.qe7.core.bus.SubscribeEvent;
import io.github.qe7.core.common.Globals;
import io.github.qe7.core.manager.AbstractManager;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.events.player.PacketEvent;
import io.github.qe7.features.commands.HelpCommand;
import io.github.qe7.toolbox.ChatUtil;
import net.minecraft.src.Packet3Chat;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class CommandManager extends AbstractManager<Class<? extends AbstractCommand>, AbstractCommand> implements Handler, Globals {

    public CommandManager() {
        List<AbstractCommand> commandList = new ArrayList<>();

        commandList.add(new HelpCommand());

        this.registerAbstractCommand(commandList);

        System.out.println("Loaded " + this.size() + " commands.");
        ManagerFactory.get(EventManager.class).registerHandler(this);
    }

    private void registerAbstractCommand(@NotNull List<AbstractCommand> abstractCommandList) {
        for (AbstractCommand command : abstractCommandList) {
            try {
                add(command.getClass(), command);
            } catch (Exception ignored) {
            }
        }
    }

    @Contract(" -> new")
    public @NotNull List<AbstractCommand> getCommands() {
        return new ArrayList<>(map.values());
    }

    public @Nullable AbstractCommand getCommandByNameOrAlias(String input) {
        for (AbstractCommand command : this.map.values()) {
            if (command.getName().equalsIgnoreCase(input))
                return command;
        }

        for (AbstractCommand command : this.map.values()) {
            for (String string: command.getAliases()) {
                if (string.equalsIgnoreCase(input))
                    return command;
            }
        }
        return null;
    }

    @SubscribeEvent
    private final Listener<PacketEvent> packetEventListener = event -> {
        if (!event.isOutgoing()) return;

        if (!(event.getPacket() instanceof Packet3Chat)) return;

        final Packet3Chat packetChatMessage = (Packet3Chat) event.getPacket();

        final String message = packetChatMessage.message;

        if (message.startsWith(".")) {
            event.setCancelled(true);
        } else {
            return;
        }

        final String[] args = message.split(" ");

        if (args.length == 0) return;

        AbstractCommand targetCommand;
        final String name = args[0].replace(".", "").toLowerCase();

        targetCommand = getCommandByNameOrAlias(name);

        if (targetCommand == null) {
            ChatUtil.addPrefixedMessage(this.getClass().getSimpleName(), "No command found.");
            return;
        }

        targetCommand.execute(args); // args[0] is always the name.
    };
}
