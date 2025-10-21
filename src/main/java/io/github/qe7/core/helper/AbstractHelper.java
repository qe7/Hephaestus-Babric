package io.github.qe7.core.helper;

import io.github.qe7.core.bus.Handler;
import io.github.qe7.core.common.Globals;

/**
 * General Helper classes will extend off of this,
 * if it doesn't need to be called to an Event it likely doesn't need to be a helper.
 */
public abstract class AbstractHelper implements Globals, Handler {
}
