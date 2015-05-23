package com.icharge.utils;

import de.greenrobot.event.EventBus;

/**
 * Created by Jinsen on 15/5/22.
 */
public class BusProvider {
    public static EventBus getDefaultBus() {
        return EventBus.getDefault();
    }
}
