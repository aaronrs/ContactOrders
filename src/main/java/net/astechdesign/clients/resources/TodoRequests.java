package net.astechdesign.clients.resources;

import net.astechdesign.clients.model.todo.Todo;
import net.astechdesign.clients.model.todo.TodoRepo;
import org.glassfish.jersey.server.mvc.Viewable;
import org.joda.time.DateTime;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Path("/todos")
@Produces(MediaType.TEXT_HTML)
public class TodoRequests {

    @GET
    public Viewable todos() throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
        Map<String, Object> data = new HashMap<>();
        Map<String, List<Todo>> todoMap = new HashMap<>();
        List<String> months = new ArrayList<>();
        for (Todo todo : TodoRepo.todos()) {
            String month = sdf.format(todo.date);
            if (!todoMap.containsKey(month)) {
                todoMap.put(month, new ArrayList<Todo>());
                months.add(month);
            }
            todoMap.get(month).add(todo);
        }
        data.put("todos", todoMap);
        data.put("months", months);
        return new Viewable("todos", data);
    }

    @POST
    @Path("/add")
    public Viewable saveTodo(@FormParam("date") String date,
                             @FormParam("notes") String notes) throws SQLException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        TodoRepo.save(new Todo(0, -1, sdf.parse(date), notes, ""));
        return todos();
    }

}
