package io.github.qe7.toolbox.animation;

import io.github.qe7.core.common.Globals;
import lombok.Getter;
import lombok.Setter;

public class TimeAnimation implements Globals {

    private Easing mode;

    @Setter
    @Getter
    private double start;
    @Setter
    @Getter
    private double target;
    @Setter
    @Getter
    private double length;

    private long lastMillis = 0L;

    private boolean state;

    public TimeAnimation(double start, double target, double speed) {
        this(start, target, speed, Easing.LINEAR);
    }

    public TimeAnimation(double start, double target, double speed, Easing mode) {
        this(false, start, target, speed, mode);
    }

    public TimeAnimation(boolean state, double start, double target, double lenght, Easing mode) {
        this.state = state;
        this.start = start;
        this.target = target;
        this.length = lenght;
        this.mode = mode;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        lastMillis = (long) (!state ? System.currentTimeMillis() - ((1 - getLinearFactor()) * length) : System.currentTimeMillis() - (getLinearFactor() * length));
        this.state = state;
    }

    public double getFactor() {
        return mode.ease(getLinearFactor());
    }

    public void setFactor(double factor) {
        long currentTime = System.currentTimeMillis();
        lastMillis = currentTime - (long) (factor * length);
    }

    public double getCurrent() {
        return start + ((target - start)) * getFactor();
    }

    public double getLinearFactor() {
        return state ? clamp(((System.currentTimeMillis() - lastMillis) / length)) : clamp((1 - (System.currentTimeMillis() - lastMillis) / length));
    }

    private double clamp(double in) {
        return in < 0 ? 0 : Math.min(in, 1);
    }

    public Easing getEasing() {
        return mode;
    }

    public void setEasing(Easing mode) {
        this.mode = mode;
    }

    public boolean isFinished() {
        return !getState() && getFactor() == 0.0 || getState() && getFactor() == 1.0;
    }
}
