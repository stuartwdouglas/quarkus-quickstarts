package org.acme.getting.started;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/bye")
public class FarewellResource {

    @Inject
    GreetingService service;

    @Inject
    ByeService byeService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/farewell/{name}")
    public String greeting(@PathParam String name) {
        return service.farewell(name);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello3() {
        return byeService.bye();
    }
}