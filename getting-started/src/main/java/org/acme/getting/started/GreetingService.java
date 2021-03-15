package org.acme.getting.started;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingService {

    public String greeting(String name) {
        return "hello" + name;
    }

    public String farewell(String name) {
        return "bye " + name;
    }
}
