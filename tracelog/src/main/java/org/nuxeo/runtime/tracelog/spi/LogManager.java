package org.nuxeo.runtime.tracelog.spi;

import java.io.Serializable;
import java.util.Map;


public interface LogManager {

    void start();

    LogManager appendTo(LogAppender appender);

    LogManager filterWith(LogFilter filter);

    void stop();

    Map<String, Serializable> getContext();

}
