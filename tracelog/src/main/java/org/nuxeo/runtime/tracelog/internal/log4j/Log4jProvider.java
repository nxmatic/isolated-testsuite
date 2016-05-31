package org.nuxeo.runtime.tracelog.internal.log4j;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import org.nuxeo.runtime.tracelog.spi.LogAppender;
import org.nuxeo.runtime.tracelog.spi.LogFilter;
import org.nuxeo.runtime.tracelog.spi.LogManager;

public class Log4jProvider implements LogManager {

    protected LogAppender sink = LogAppender.NULL;

    protected LogFilter filter = LogFilter.OPEN;

    protected Set<Logger> lastLoggers = new HashSet<Logger>();

    @Override
    public void start() {
        final Enumeration<Logger> loggers = org.apache.log4j.LogManager.getCurrentLoggers();
        while (loggers.hasMoreElements()) {
            lastLoggers.add(loggers.nextElement());
        }
        org.apache.log4j.LogManager.resetConfiguration();
        final AppenderSkeleton appender = new AppenderSkeleton() {

            @Override
            public boolean requiresLayout() {
                return false;
            }

            @Override
            public void close() {
                ;
            }

            @Override
            protected void append(LoggingEvent event) {
                sink.append(new EventAdapter(event));
            }
        };
        appender.addFilter(new Filter() {

            @Override
            public int decide(LoggingEvent event) {
                if (filter.accept(new EventAdapter(event))) {
                    return Filter.ACCEPT;
                }
                return Filter.DENY;
            }
        });
        org.apache.log4j.LogManager.getRootLogger().addAppender(appender);
    }

    @Override
    public org.nuxeo.runtime.tracelog.spi.LogManager appendTo(LogAppender appender) {
        sink  = appender;
        return this;
    }

    @Override
    public LogManager filterWith(LogFilter filter) {
        this.filter = filter;
        return this;
    }

    @Override
    public void stop() {

    }

    @Override
    public Map<String, Serializable> getContext() {
        MDC.put("pfouh", "pfouh");
        return MDC.getContext();
    }


}
