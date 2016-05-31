package org.nuxeo.runtime.tracelog.spi;


public interface LogFilter {

    boolean accept(LogEvent event);

    LogFilter OPEN = new LogFilter() {

        @Override
        public boolean accept(LogEvent event) {
            return true;
        }
    };
}
