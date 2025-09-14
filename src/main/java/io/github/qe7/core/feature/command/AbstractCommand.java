package io.github.qe7.core.feature.command;

import io.github.qe7.core.common.Descriptionable;
import io.github.qe7.core.common.Executable;
import io.github.qe7.core.common.Global;
import io.github.qe7.core.common.Nameable;

import java.util.ArrayList;

public abstract class AbstractCommand implements Global, Nameable, Descriptionable, Executable {

    private final String name, description;
    private ArrayList<String> alises;

    public AbstractCommand(final String name, final String description) {
        this.name = name;
        this.description = description;
        alises = new ArrayList<String>();
    }

    public void addAlis(String alises) {
        this.alises.add(alises);
    }


    @Override
    public String getName() {
        return this.name;
    }

    public ArrayList<String> getAlises() {
        return this.alises;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
