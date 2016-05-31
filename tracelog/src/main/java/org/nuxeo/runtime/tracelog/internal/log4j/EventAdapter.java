package org.nuxeo.runtime.tracelog.internal.log4j;

import java.io.Serializable;

import org.apache.log4j.MDC;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.nuxeo.runtime.tracelog.spi.LogEvent;

public class EventAdapter implements LogEvent {

    private static final long serialVersionUID = 1L;

    protected final transient LoggingEvent source;

    protected EventAdapter(LoggingEvent source) {
        this.source = source;
    }

    @Override
    public long getTimestamp() {
        return source.getTimeStamp();
    }

    @Override
    public String getLogger() {
        return source.getLoggerName();
    }

    @Override
    public int getLevel() {
        return source.getLevel().toInt();
    }

    @Override
    public Serializable getMessage() {
        return (Serializable) source.getMessage();
    }

    @Override
    public String getRenderedMessage() {
        return source.getRenderedMessage();
    }

    @Override
    public StackTraceElement getLocation() {
        LocationInfo info = source.getLocationInformation();
        return new StackTraceElement(info.getClassName(), info.getMethodName(),
                info.getFileName(), Integer.parseInt(info.getLineNumber()));
    }

    @Override
    public Serializable getContext() {
        return MDC.getContext();
    }

}
