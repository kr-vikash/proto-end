package com.protoend.util;

import org.apache.catalina.core.ApplicationContext;
import org.apache.catalina.core.StandardContext;

public class ApplicationContextState extends ApplicationContext {
    public ApplicationContextState(StandardContext context) {
        super(context);
    }
}
