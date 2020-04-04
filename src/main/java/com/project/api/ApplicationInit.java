package com.project.api;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class ApplicationInit extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(PersonResource.class);
        classes.add(HealthCheck.class);
        return classes;
    }
}
