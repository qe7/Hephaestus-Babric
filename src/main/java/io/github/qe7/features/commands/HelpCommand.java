package io.github.qe7.features.commands;

import io.github.qe7.core.feature.command.AbstractCommand;
import io.github.qe7.core.feature.command.CommandManager;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.toolbox.ChatUtil;

import java.util.List;

public final class HelpCommand extends AbstractCommand {

    public HelpCommand() {
        super("Help", "Displays a list of commands and their descriptions.");
    }

    @Override
    public void execute(String[] args) {
        List<AbstractCommand> commandList = ManagerFactory.get(CommandManager.class).getCommands();

        for (AbstractCommand command : commandList) {
            List<String> aliasArray = command.getAliases();
            String message;
            if (!aliasArray.isEmpty()) {
                message = command.getName() + " (" + String.join(", ", aliasArray) + ")";
            } else {
                message = command.getName();
            }
            ChatUtil.addPrefixedMessage(this.getName(), message);
            ChatUtil.addPrefixedMessage("-", command.getDescription());
        }
    }
}
