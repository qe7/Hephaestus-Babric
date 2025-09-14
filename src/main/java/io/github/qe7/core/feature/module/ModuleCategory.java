package io.github.qe7.core.feature.module;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

@AllArgsConstructor
@Getter
public enum ModuleCategory {
    COMBAT("Combat", new Color(220, 40, 40).getRGB()),
    MISC("Misc", new Color(80, 200, 120).getRGB()),
    MOVEMENT("Movement", new Color(40, 120, 220).getRGB()),
    RENDER("Render", new Color(255, 180, 70).getRGB()),
    EXPLOIT("Exploit", new Color(170, 60, 220).getRGB());

    private final String name;
    private final int color;
}
