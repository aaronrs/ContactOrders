package net.astechdesign.clients.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.astechdesign.clients.model.contact.*;
import net.astechdesign.clients.model.product.Product;
import net.astechdesign.clients.model.product.ProductRepo;
import net.astechdesign.clients.model.todo.Todo;
import net.astechdesign.clients.model.todo.TodoRepo;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.*;

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
