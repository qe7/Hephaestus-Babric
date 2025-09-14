package io.github.qe7.platform.ui.api.component.slider;

import io.github.qe7.toolbox.MathUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class SliderHandler<N extends Number> {

    private final Supplier<N> input;
    private final Consumer<N> output;

    public SliderHandler(Supplier<N> input, Consumer<N> output) {
        this.input = input;
        this.output = output;
    }

    public N getValue() {
        return input.get();
    }

    public float getPercent() {
        return percentFromValue(getValue());
    }

    protected abstract N valueFromPercent(float percent);

    protected abstract float percentFromValue(N value);

    protected abstract N parseText(String text);

    public void handleDrag(float drag) {
        output.accept(valueFromPercent(drag));
    }

    public void handleText(String text) {
        try {
            output.accept(parseText(text));
        } catch (NumberFormatException ignored) {
        }
    }

    public static SliderHandler<Float> simpleFloatHandler(Supplier<Float> inputHook, Consumer<Float> outputHook, float min, float max) {
        return new SliderHandler(inputHook, outputHook) {
            @Override
            protected Float valueFromPercent(float percent) {
                return MathUtil.getValue(min, percent, max);
            }

            @Override
            protected float percentFromValue(Number value) {
                return MathUtil.getPercent(min, value.floatValue(), max);
            }

            @Override
            protected Float parseText(String text) {
                return Float.parseFloat(text);
            }
        };
    }

    public static SliderHandler<Float> simpleHandler(Supplier<Float> inputHook, Consumer<Float> outputHook) {
        return new SliderHandler(inputHook, outputHook) {
            @Override
            protected Float valueFromPercent(float percent) {
                return Math.max(0f, Math.min(1f, percent));
            }

            @Override
            protected float percentFromValue(Number value) {
                return MathUtil.getPercent(0f, value.floatValue(), 1f);
            }

            @Override
            protected Float parseText(String text) {
                return Float.parseFloat(text);
            }
        };
    }

    public static SliderHandler<Integer> simpleIntHandler(Supplier<Integer> inputHook, Consumer<Integer> outputHook, int min, int max) {
        return new SliderHandler(inputHook, outputHook) {
            @Override
            protected Integer valueFromPercent(float percent) {
                return MathUtil.getValue(min, percent, max);
            }

            @Override
            protected float percentFromValue(Number value) {
                return MathUtil.getPercent(min, value.intValue(), max);
            }

            @Override
            protected Integer parseText(String text) {
                return Integer.parseInt(text);
            }
        };
    }
}
