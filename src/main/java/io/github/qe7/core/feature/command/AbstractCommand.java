package io.github.qe7.core.feature.command;

import io.github.qe7.core.common.Descriptionable;
import io.github.qe7.core.common.Executable;
import io.github.qe7.core.common.Global;
import io.github.qe7.core.common.Nameable;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public abstract class AbstractCommand implements Global, Nameable, Descriptionable, Executable {

    private final String name, description;
    private final ArrayList<String> aliases;

    public AbstractCommand(final String name, final String description) {
        this.name = name;
        this.description = description;
        this.aliases = new ArrayList<>();
    }

    public void addAlias(String alias) {
        this.aliases.add(alias);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
