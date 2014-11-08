package net.astechdesign.clients.resources;

import net.astechdesign.clients.server.JerseyServer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/stop")
public class SystemRequests {

    @GET
    public void stop() throws Exception {
        JerseyServer.server.stop();
    }
}
