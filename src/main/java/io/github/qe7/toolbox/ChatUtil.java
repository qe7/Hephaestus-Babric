package io.github.qe7.toolbox;

import io.github.qe7.core.common.Global;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ChatUtil implements Global {

    public static void addMessage(final String message) {
        if (mc.thePlayer == null) return;

        mc.thePlayer.addChatMessage(message);
    }

    public static void addPrefixedMessage(final String prefix, final String message) {
        addMessage("§7[" + prefix + "] §r§f" + message);
    }

    public static void sendMessage(final String message) {
        if (mc.thePlayer == null) return;

        mc.thePlayer.sendChatMessage(message);
    }
}
