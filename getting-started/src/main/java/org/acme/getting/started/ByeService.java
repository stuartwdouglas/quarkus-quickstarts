package org.acme.getting.started;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ByeService {

    public String  bye() {
        return "bye";
    }

}
