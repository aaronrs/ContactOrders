package net.astechdesign.contacts.resources;

import com.google.common.io.CharStreams;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Path("/static")
public class StaticResources {


    @GET
    @Path("/{name}")
    @Produces({MediaType.TEXT_HTML, "text/css", "application/javascript"})
    public String get(@PathParam("name") String name) throws IOException {
        InputStream inputStream = StaticResources.class.getClassLoader().getResourceAsStream(name);
        return CharStreams.toString(new InputStreamReader(inputStream));
    }


}
