package ca.fun.simplyspend.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("home")
public class Home {


    @Path("hello")
    @GET
    @Produces("text/plain")
    public String home() {
        return "Hello World";
    }
}
