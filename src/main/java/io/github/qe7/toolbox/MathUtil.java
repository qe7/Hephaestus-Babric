package io.github.qe7.toolbox;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class MathUtil {

    public static int getValue(int min, float percent, int max) {
        return Math.min(max, Math.max(min, (int) (min + (max - min) * percent)));
    }

    public static float getValue(float min, float percent, float max) {
        return Math.min(max, Math.max(min, min + (max - min) * percent));
    }

    public static float getPercent(float min, float value, float max) {
        return Math.max(0f, Math.min(1f, (value - min) / (max - min)));
    }
}
