package org.nuxeo.runtime.tracelog.test;


import java.io.File;

import org.junit.runners.model.FrameworkMethod;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.SimpleFeature;
import org.nuxeo.runtime.tracelog.internal.TraceContext;
import org.nuxeo.runtime.tracelog.internal.TraceEnabler;
import org.nuxeo.runtime.tracelog.internal.TraceStore;

import com.google.inject.Binder;

public class TraceLogFeature extends SimpleFeature {

    TraceStore store = new TraceStore(new File("target/traces"));

    TraceEnabler enabler = new TraceEnabler(store);

    TraceContext context;

    @Override
    public void initialize(FeaturesRunner runner) throws Exception {
        context = enabler.enable(true);
    }

    @Override
    public void configure(FeaturesRunner runner, Binder binder) {
        binder.bind(TraceLogFeature.class).toInstance(this);
    }


    @Override
    public void stop(FeaturesRunner runner) throws Exception {
        enabler.disable();
    }

    @Override
    public void beforeRun(FeaturesRunner runner) throws Exception {
        context.classEntered(runner.getDescription().getTestClass());
    }

    @Override
    public void afterRun(FeaturesRunner runner) throws Exception {
        context.classLeft(runner.getDescription().getTestClass());
    }

    @Override
    public void beforeMethodRun(FeaturesRunner runner, FrameworkMethod method,
            Object test) throws Exception {
        context.methodEntered(method.getMethod());
    }

    @Override
    public void afterMethodRun(FeaturesRunner runner, FrameworkMethod method,
            Object test) throws Exception {
        context.methodLeft(method.getMethod());
    }

}
