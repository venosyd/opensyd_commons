package com.venosyd.open.commons.util;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         Misc utilities for ID generation
 */
public enum IDGenerator {

    INSTANCE;

    /** */
    private AtomicLong counter = new AtomicLong(-1);

    /** */
    public Long generateID() {
        return counter.incrementAndGet();
    }

    /** */
    public String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
