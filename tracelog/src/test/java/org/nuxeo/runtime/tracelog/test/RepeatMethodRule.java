package org.nuxeo.runtime.tracelog.test;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class RepeatMethodRule implements MethodRule {

    @Override
    public Statement apply(final Statement base, FrameworkMethod method, Object target) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                for (int i = 0; i < 100000; ++i) {
                    base.evaluate();
                }
            }

        };
    }

}
