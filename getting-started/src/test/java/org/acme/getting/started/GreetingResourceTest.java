package org.acme.getting.started;

import java.util.UUID;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class GreetingResourceTest {

    @Inject
    GreetingResource greetingResource;

    @Test
    public void testHelloEndpoint() {
        Assertions.assertEquals("hello", greetingResource.hello());
    }

    @Test
    public void testGreetingEndpoint() {
        String uuid = UUID.randomUUID().toString();
        Assertions.assertEquals("hello " + uuid, greetingResource.greeting(uuid));
    }

}
