package org.nuxeo.runtime.tracelog.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import javax.inject.Inject;

import org.apache.commons.logging.LogConfigurationException;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

@RunWith(FeaturesRunner.class)
@Features(TraceLogFeature.class)
public class CanTraceLog {

    @Inject
    TraceLogFeature feature;

    @Test
    public void canWrite() {
        logEvent();
        assertFalse("empty database", feature.store.events.isEmpty());
    }

    protected void logEvent() throws LogConfigurationException {
        LogFactory.getLog(CanTraceLog.class).debug("a log",
                new Throwable("stack trace"));
    }

    @Test
    public void canReadback() {
        logEvent();
        int wroteSize = feature.store.events.size();
        feature.enabler.disable();
        feature.enabler.enable(false);
        assertEquals("wrong readback events count", wroteSize,
                feature.store.events.size());
    }

}
