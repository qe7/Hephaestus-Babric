package io.github.qe7.toolbox.animation;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Animation {

    private Easing easing;
    private long duration;
    private long startTime;
    private double startValue;
    private double destinationValue;
    private double value;
    private boolean finished;

    private Direction direction = Direction.FORWARDS;

    public Animation(final Easing easing, final long duration) {
        this.easing = easing;
        this.duration = duration;
        this.startTime = System.currentTimeMillis();
    }

    public void run(final double destinationValue) {
        if (this.destinationValue != destinationValue) {
            this.destinationValue = destinationValue;
            this.reset();
        } else {
            this.finished = System.currentTimeMillis() - this.startTime >= this.duration;

            if (this.finished) {
                this.value = destinationValue;
                return;
            }
        }

        final double progress = this.getProgress();
        final double result = this.easing.getFunction().apply(progress);

        if (this.direction == Direction.FORWARDS) {
            this.value = this.startValue + (destinationValue - this.startValue) * result;
        } else {
            this.value = this.startValue - (this.startValue - destinationValue) * result;
        }
    }

    public double getProgress() {
        return (double) (System.currentTimeMillis() - this.startTime) / (double) this.duration;
    }

    public void reset() {
        this.startTime = System.currentTimeMillis();
        this.startValue = this.value;
        this.finished = false;
    }
}
