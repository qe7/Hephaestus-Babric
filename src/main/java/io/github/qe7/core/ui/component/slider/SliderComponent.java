package io.github.qe7.core.ui.component.slider;

import io.github.qe7.core.ui.TextHandler;
import io.github.qe7.core.ui.component.AbstractComponent;
import org.lwjgl.input.Keyboard;

import java.util.function.Supplier;

public class SliderComponent extends AbstractComponent {

    private final SliderHandler<?> handler;
    private final TextHandler textHandler = new TextHandler();
    private boolean listening = false;
    private boolean dragging = false;

    public SliderComponent(String name, Supplier<Boolean> visibility, SliderHandler<?> sliderHandler) {
        super(visibility, name);
        this.handler = sliderHandler;
    }

    public SliderComponent(SliderHandler<?> handler) {
        this("SliderComponent", () -> true, handler);
    }

    protected void drawSlider(String value, float fill) {
        getTheme().renderSliderComponent(this, value, fill);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        setHeight(getTheme().getDefaultHeaderHeight());

        if (dragging) {
            float click = (mouseX - getX()) / getWidth();
            handler.handleDrag(click);
        }

        double val = 0.0;
        boolean floating = false;
        Class<? extends Number> type = handler.getValue().getClass();
        if (type != Integer.class) {
            floating = true;
            val = handler.getValue().doubleValue();
        }

        String text = listening ? textHandler.getIdlingText() : floating ? String.valueOf(Math.round(val * 100.0) / 100.0) : handler.getValue().toString();

        drawSlider(text, handler.getPercent());
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
                handler.handleText(textHandler.getText());
                listening = false;
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isMouseOver(mouseX, mouseY, getHeight())) {
            if (mouseButton == 0) {
                dragging = true;
                if (listening) {
                    listening = false;
                    textHandler.reset();
                }
            } else if (mouseButton == 1) {
                listening = true;
                textHandler.setText("");
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (dragging) {
            dragging = false;
        }
    }
}
