package org.nuxeo.runtime.tracelog.internal;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.runner.Description;
import org.nuxeo.runtime.tracelog.spi.LogAppender;
import org.nuxeo.runtime.tracelog.spi.LogEvent;

public class TraceWriter implements LogAppender {

    protected final Description desc;

    protected final Map<Long,LogEvent> db ;

    protected final AtomicLong index = new AtomicLong( 0 );

    protected TraceWriter(Description desc, Map<Long, LogEvent> db) {
        this.desc = desc;
        this.db = db;
    }


    @Override
    public void append(LogEvent event) {
        db.put(index.incrementAndGet(), event);
    }


    @Override
    public String getName() {
        return TraceWriter.class.getName();
    }


    @Override
    public void reset() {
        ;
    }
}
