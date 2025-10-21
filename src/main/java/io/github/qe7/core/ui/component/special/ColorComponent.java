package io.github.qe7.core.ui.component.special;

import io.github.qe7.core.ui.DragHandler;
import io.github.qe7.core.ui.TextHandler;
import io.github.qe7.core.ui.component.AbstractComponent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ColorComponent extends AbstractComponent {

    private final Supplier<Color> colorSupplier;
    private final Consumer<Color> colorConsumer;

    private final DragHandler sbDrag = new DragHandler(0, 0);
    private final DragHandler hueDrag = new DragHandler(0, 0);
    private final DragHandler alphaDrag = new DragHandler(0, 0);
    public final TextHandler hexBox = new TextHandler();

    private boolean open = false;
    private boolean typingHex = false;

    private float hue, saturation, brightness;
    private int alpha;

    public ColorComponent(String name, Supplier<Boolean> visible, Supplier<Color> colorSupplier, Consumer<Color> colorConsumer) {
        super(visible, name);
        this.colorSupplier = colorSupplier;
        this.colorConsumer = colorConsumer;

        Color c = colorSupplier.get();
        float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
        hue = hsb[0];
        saturation = hsb[1];
        brightness = hsb[2];
        alpha = c.getAlpha();

        hexBox.setText(toHex(c));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (getTheme() == null) return;

        float baseHeight = getTheme().getDefaultHeaderHeight();
        float totalWidth = getWidth();

        // Uniform spacing calculation
        float uniformSpacing = Math.max(4f, totalWidth * 0.02f);
        float sliderWidth = Math.max(6f, totalWidth * 0.05f);

        // Consistent picker size calculation
        float pickerSize = totalWidth - (sliderWidth * 2f + uniformSpacing * 4f);
        float hexHeight = 14f;
        float pickerHeight = pickerSize + hexHeight + uniformSpacing * 2f;

        setHeight(open ? baseHeight + pickerHeight : baseHeight);

        Color current = getCurrentColor();
        getTheme().renderColorComponent(this, current, open);

        if (!open) return;

        getTheme().renderColorPicker(this, hue, saturation, brightness, alpha, hexBox.getText(), typingHex);

        float pickerX = getX() + uniformSpacing;
        float pickerY = getY() + baseHeight + uniformSpacing - 3;
        float hueX = pickerX + pickerSize + uniformSpacing;
        float alphaX = hueX + sliderWidth + uniformSpacing;

        // Update dragging logic
        if (sbDrag.isDragging()) {
            float relX = clamp((mouseX - pickerX) / pickerSize, 0f, 1f);
            float relY = clamp((mouseY - pickerY) / pickerSize, 0f, 1f);
            saturation = relX;
            brightness = 1f - relY;
            pushColor();
        } else if (hueDrag.isDragging()) {
            float relY = clamp((mouseY - pickerY) / pickerSize, 0f, 1f);
            hue = relY;
            pushColor();
        } else if (alphaDrag.isDragging()) {
            float relY = clamp((mouseY - pickerY) / pickerSize, 0f, 1f);
            alpha = Math.round((1f - relY) * 255f);
            pushColor();
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        float headerHeight = getTheme().getDefaultHeaderHeight();
        boolean headerHovered = isMouseOver(mouseX, mouseY, headerHeight);

        if (headerHovered) {
            open = !open;
            return;
        }

        if (!open) return;

        float totalWidth = getWidth();
        float uniformSpacing = Math.max(4f, totalWidth * 0.02f);
        float sliderWidth = Math.max(6f, totalWidth * 0.05f);
        float pickerSize = totalWidth - (sliderWidth * 2f + uniformSpacing * 4f);

        float pickerX = getX() + uniformSpacing;
        float pickerY = getY() + headerHeight + uniformSpacing;
        float hueX = pickerX + pickerSize + uniformSpacing;
        float alphaX = hueX + sliderWidth + uniformSpacing;

        float hexHeight = 14f;
        float hexY = pickerY + pickerSize + uniformSpacing;

        // Calculate total width from picker start to alpha slider end
        float totalControlsWidth = (alphaX + sliderWidth) - pickerX;

        // Buttons layout - span the entire controls width
        float btnSpacing = uniformSpacing;
        float btnWidth = Math.max(20f, totalControlsWidth * 0.15f); // Adjust button width proportionally
        float hexWidth = totalControlsWidth - (btnWidth * 2f + btnSpacing * 2f);

        sbDrag.handleMouseClicked(mouseX, mouseY, button,
                isInside(mouseX, mouseY, pickerX, pickerY, pickerSize, pickerSize));
        hueDrag.handleMouseClicked(mouseX, mouseY, button,
                isInside(mouseX, mouseY, hueX, pickerY, sliderWidth, pickerSize));
        alphaDrag.handleMouseClicked(mouseX, mouseY, button,
                isInside(mouseX, mouseY, alphaX, pickerY, sliderWidth, pickerSize));

        // Hex field click - starts at pickerX and uses calculated hexWidth
        if (isInside(mouseX, mouseY, pickerX, hexY, hexWidth, hexHeight) && button == 0) {
            typingHex = true;
            hexBox.reset();
            return;
        }

        // Copy button click
        float copyX = pickerX + hexWidth + btnSpacing;
        if (isInside(mouseX, mouseY, copyX, hexY, btnWidth, hexHeight) && button == 0) {
            copyToClipboard(hexBox.getText());
        }

        // Paste button click
        float pasteX = copyX + btnWidth + btnSpacing;
        if (isInside(mouseX, mouseY, pasteX, hexY, btnWidth, hexHeight) && button == 0) {
            String clip = getClipboard();
            if (clip != null && clip.startsWith("#")) {
                applyHex(clip);
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        sbDrag.setDragging(false);
        hueDrag.setDragging(false);
        alphaDrag.setDragging(false);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (!typingHex) return;

        if (keyCode == Keyboard.KEY_RETURN) {
            applyHex(hexBox.getText());
            typingHex = false;
            return;
        }
        if (keyCode == Keyboard.KEY_ESCAPE) {
            typingHex = false;
            return;
        }

        hexBox.update(typedChar);
    }

    private void pushColor() {
        Color c = getCurrentColor();
        colorConsumer.accept(c);
        hexBox.setText(toHex(c));
    }

    private Color getCurrentColor() {
        Color base = Color.getHSBColor(hue, saturation, brightness);
        return new Color(base.getRed(), base.getGreen(), base.getBlue(), alpha);
    }

    private void applyHex(String hex) {
        if (hex == null) return;
        String clean = hex.trim().replace("#", "");
        try {
            if (clean.length() == 6 || clean.length() == 8) {
                int r = Integer.parseInt(clean.substring(0, 2), 16);
                int g = Integer.parseInt(clean.substring(2, 4), 16);
                int b = Integer.parseInt(clean.substring(4, 6), 16);
                int a = (clean.length() == 8)
                        ? Integer.parseInt(clean.substring(6, 8), 16)
                        : 255;
                Color c = new Color(r, g, b, a);
                float[] hsb = Color.RGBtoHSB(r, g, b, null);
                hue = hsb[0];
                saturation = hsb[1];
                brightness = hsb[2];
                alpha = a;
                pushColor();
            }
        } catch (NumberFormatException ignored) {}
    }

    private String toHex(Color c) {
        return String.format("#%02X%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
    }

    private void copyToClipboard(String text) {
        try {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
        } catch (Exception ignored) {}
    }

    private String getClipboard() {
        try {
            return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
            return null;
        }
    }

    private boolean isInside(float mx, float my, float x, float y, float w, float h) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    private float clamp(float v, float min, float max) {
        return Math.max(min, Math.min(max, v));
    }
}