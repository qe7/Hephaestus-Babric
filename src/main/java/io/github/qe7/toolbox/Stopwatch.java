package io.github.qe7.toolbox;

import java.util.concurrent.TimeUnit;

public class Stopwatch {

    private long lastNS = System.nanoTime();

    public boolean hasTimeElapsed(long time, TimeUnit unit) {
        return hasTimeElapsed(time, unit, false);
    }

    public boolean hasTimeElapsed(long time, TimeUnit unit, boolean reset) {
        long elapsedNS = System.nanoTime() - lastNS;
        long timeoutNS = unit.toNanos(time);

        if (elapsedNS >= timeoutNS) {
            if (reset) {
                reset();
            }
            return true;
        }
        return false;
    }

    public void reset() {
        lastNS = System.nanoTime();
    }

    public long getTime(TimeUnit unit) {
        long elapsedNS = System.nanoTime() - lastNS;
        return unit.convert(elapsedNS, TimeUnit.NANOSECONDS);
    }
}