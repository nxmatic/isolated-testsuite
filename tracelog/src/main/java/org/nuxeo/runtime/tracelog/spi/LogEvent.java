package org.nuxeo.runtime.tracelog.spi;

import java.io.Serializable;

public interface LogEvent extends Serializable {

    long getTimestamp();

    String getLogger();

    int getLevel();

    Serializable getContext();

    Serializable getMessage();

    String getRenderedMessage();

    StackTraceElement getLocation();
}
