package org.nuxeo.runtime.tracelog.spi;



public interface LogAppender {

    String getName();

    void reset();

    void append(LogEvent event);

    LogAppender NULL = new LogAppender() {

        @Override
        public void reset() {

        }

        @Override
        public String getName() {
            return LogAppender.class.getName().concat("$NULL");
        }

        @Override
        public void append(LogEvent event) {
            ;
        }
    };
}
