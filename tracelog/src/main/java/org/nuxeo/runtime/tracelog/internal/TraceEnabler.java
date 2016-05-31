package org.nuxeo.runtime.tracelog.internal;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

import org.junit.runner.Description;
import org.nuxeo.runtime.tracelog.spi.LogEvent;
import org.nuxeo.runtime.tracelog.spi.LogManager;

public class TraceEnabler {

    protected final TraceStore store;

    protected TraceWriter appender;

    protected static TraceEnabler INSTANCE = new TraceEnabler();

    protected final LogManager manager = loadManager();

    protected TraceEnabler() {
        this(new TraceStore(new File("target/traces")));
    }

    protected LogManager loadManager() {
        Iterator<LogManager> managers = ServiceLoader.load(LogManager.class).iterator();
        if (managers.hasNext()) {
            return managers.next();
        }
        throw new Error("Cannot load log manager service from classpath");
    }

    public TraceEnabler(TraceStore store) {
        this.store = store;
    }

    public TraceContext enable(Description description) {
        final Map<Long, LogEvent> events = store.get(false, description);
        manager.appendTo(new TraceWriter(description, events));
        manager.start();
        Map<String, Serializable> aMap = manager.getContext();
        return new TraceContext(aMap);
    }

    public void disable() {
        store.release();
        manager.stop();
    }

    public void classEntered(Class<?> testClass) {
        manager.getContext().put("suite", testClass.getName());
    }

    public void classLeft(Class<?> testClass) {
        manager.getContext().remove("suite");
    }

    public void methodEntered(Method method) {
        manager.getContext().put("test", method.getName());
    }


    public void methodLeft(Method method) {
        manager.getContext().remove("test");
    }
}
