package org.nuxeo.runtime.tracelog.internal;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

public class TraceContext {

    protected TraceContext(Map<String,Serializable> aMap) {
        theMap = aMap;
    }

    protected final Map<String,Serializable> theMap;

    public void classEntered(Class<?> testClass) {
        theMap.put("suite", testClass.getName());
    }

    public void classLeft(Class<?> testClass) {
        theMap.remove("suite");
    }

    public void methodEntered(Method method) {
        theMap.put("test", method.getName());
    }


    public void methodLeft(Method method) {
        theMap.remove("test");
    }
}
