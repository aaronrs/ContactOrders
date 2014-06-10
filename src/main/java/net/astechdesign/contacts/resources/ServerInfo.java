package net.astechdesign.contacts.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/server")
public class ServerInfo {

    public static boolean SERVER_RUNNING = true;
    @GET
    public String stopServer(){
        SERVER_RUNNING = false;
        return "success";
    }
}
