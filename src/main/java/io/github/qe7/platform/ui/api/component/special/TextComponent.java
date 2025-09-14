package io.github.qe7.platform.ui.api.component.special;

import io.github.qe7.platform.ui.api.TextHandler;
import io.github.qe7.platform.ui.api.component.AbstractComponent;
import org.lwjgl.input.Keyboard;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class TextComponent extends AbstractComponent {

    private final TextHandler textHandler = new TextHandler();
    private boolean listening = false;

    private final Consumer<String> handler;

    public TextComponent(String name, Supplier<Boolean> visibilitySupplier, Supplier<String> input, Consumer<String> handler) {
        super(visibilitySupplier, name);
        this.handler = handler;
        this.textHandler.setText(input.get());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (getTheme() == null) {
            return;
        }

        setHeight(getTheme().getDefaultHeaderHeight());
        getTheme().renderTextComponent(this, listening ? textHandler.getIdlingText() : textHandler.getText());
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (listening) {
            textHandler.update(typedChar);

            if (keyCode == Keyboard.KEY_ESCAPE) {
                textHandler.reset();
                listening = false;
            }

            if (keyCode == Keyboard.KEY_RETURN) {
                String text = textHandler.getText();
                if (!text.isEmpty()) {
                    handler.accept(text);
                }
                listening = false;
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isMouseOver(mouseX, mouseY, getHeight()) && mouseButton == 0) {
            listening = !listening;
            if (listening) {
                textHandler.reset();
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {}
}
