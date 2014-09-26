package net.astechdesign.clients.resources;

import net.astechdesign.clients.model.todo.TodoRepo;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Path("/todos")
@Produces(MediaType.TEXT_HTML)
public class TodoRequests {

    @GET
    public Viewable todos() throws SQLException {
        Map<String, Object> data = new HashMap<>();
        data.put("todos", TodoRepo.todos());
        return new Viewable("todos", data);
    }
}
