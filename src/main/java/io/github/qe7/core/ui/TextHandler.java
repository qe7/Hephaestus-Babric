package io.github.qe7.core.ui;

import io.github.qe7.toolbox.Stopwatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TextHandler {

    private final Stopwatch idleTimer = new Stopwatch();
    private boolean idling;
    private String text;
    private String fallback;

    public String getIdlingText() {
        return text + getIdleSign();
    }

    public void setText(String text) {
        this.text = text;
        this.fallback = text;
    }

    public void reset() {
        setText(fallback);
    }

    public void append(String text) {
        setText(getText() + text);
    }

    public void update(char chr) {
        // handle backspace
        if (chr == '\b') {
            setText(removeLastChar(getText()));
            return;
        }

        // TODO: Make a better way to handle this
        if (chr < 32 || chr > 126) {
            return;
        }

        setText(getText() + chr);
    }

    public void delete() {
        setText(removeLastChar(getText()));
    }

    /**
     * If we ever need to do cursor clicking or selection dragging.
     */
    public void updateCursor(float mouseX, float mouseY, int button) {
    }

    public List<String> getTextLines(int wrap) {
//        return StringUtil.wrapWords(getText(), wrap);
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        String[] words = getText().split(" ");

        for (String word : words) {
            if (currentLine.length() + word.length() + 1 <= wrap) {
                if (currentLine.length() > 0) {
                    currentLine.append(" ");
                }
                currentLine.append(word);
            } else {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            }
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }

    public static String removeLastChar(String str) {
        String output = "";
        if (str != null && !str.isEmpty()) {
            output = str.substring(0, str.length() - 1);
        }

        return output;
    }

    public String getIdleSign() {
        if (idleTimer.hasTimeElapsed(500, TimeUnit.MILLISECONDS)) {
            idling = !idling;
            idleTimer.reset();
        }

        if (idling) {
            return "_";
        }

        return "";
    }

    public String getText() {
        return text;
    }
}
