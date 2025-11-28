package io.github.qe7.toolbox;

import io.github.qe7.core.common.Globals;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ChatUtil implements Globals {

    public static void addMessage(final String message) {
        if (mc.thePlayer == null) return;

        mc.thePlayer.addChatMessage(message);
    }

    public static void addPrefixedMessage(final String prefix, final String message) {
        addMessage(String.format("%s[%s] %s", Formatting.GRAY, prefix, Formatting.RESET + message));
    }

    public static void sendMessage(final String message) {
        if (mc.thePlayer == null) return;

        mc.thePlayer.sendChatMessage(message);
    }

    public enum Formatting {
        BLACK('0'),
        DARK_BLUE('1'),
        DARK_GREEN('2'),
        DARK_AQUA('3'),
        DARK_RED('4'),
        DARK_PURPLE('5'),
        GOLD('6'),
        GRAY('7'),
        DARK_GRAY('8'),
        BLUE('9'),
        GREEN('a'),
        AQUA('b'),
        RED('c'),
        LIGHT_PURPLE('d'),
        YELLOW('e'),
        WHITE('f'),
        RESET('r');

        private final char code;
        private final boolean isFormat;
        private final String toString;

        Formatting(char code) {
            this(code, false);
        }

        Formatting(char code, boolean isFormat) {
            this.code = code;
            this.isFormat = isFormat;
            this.toString = "§" + code;
        }

        @Override
        public String toString() {
            return this.toString;
        }
    }
}
