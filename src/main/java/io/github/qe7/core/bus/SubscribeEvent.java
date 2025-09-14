package io.github.qe7.core.bus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@SuppressWarnings("unused")
public @interface SubscribeEvent {
    EventPriority value() default EventPriority.NORMAL;
}