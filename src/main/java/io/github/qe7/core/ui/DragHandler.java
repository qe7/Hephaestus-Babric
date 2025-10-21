package io.github.qe7.core.ui;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DragHandler {

    private float x;
    private float y;
    private float clickedX;
    private float clickedY;
    private boolean dragging;

    public DragHandler(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void handleRender(float mouseX, float mouseY) {
        if (dragging) {
            float resultX = mouseX + clickedX;
            float resultY = mouseY + clickedY;
            x = resultX;
            y = resultY;
        }
    }

    public void handleMouseClicked(float mouseX, float mouseY, int button, boolean hovered) {
        dragging = false;
        if (hovered && button == 0) {
            dragging = true;
            clickedX = x - mouseX;
            clickedY = y - mouseY;
        }
    }

}
