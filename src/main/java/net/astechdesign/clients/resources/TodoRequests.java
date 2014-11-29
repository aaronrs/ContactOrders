package net.astechdesign.clients.resources;

import net.astechdesign.clients.model.todo.Todo;
import net.astechdesign.clients.model.todo.TodoRepo;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//
//    private Map<String, List<Todo>> normaliseTodos() throws SQLException {
//        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
////        Map<String, List<Todo>> todoMap = new HashMap<>();
//
//        Map<String, List<Map<Date, List<Map<Contact, List<String>>>>>> todoMap = new HashMap<>();
//
//        List<String> months = new ArrayList<>();
//
//        List<String> orderList = null;
//        String oldMonth = "";
//        int oldId = -1;
//        Date oldDate = Date.from(Instant.MIN);
//        for (Todo todo : TodoRepo.todos()) {
//            String month = sdf.format(todo.date);
//            if (month.equals(oldMonth)) {
//                if (todo.date.equals(oldDate)) {
//                    if (todo.contactId == oldId) {
//                        orderList.add(todo.notes);
//                    } else {
//                        orderList = new ArrayList<>();
//                        Map<Contact, List<String>> contactMap = new HashMap<>();
//                        contactMap.put(new Contact(todo.contactId, todo.name, null, null, null), orderList);
//                        orderList.add(todo.notes);
//                    }
//                } else {
//
//                }
//            }
//
//            if (!todoMap.containsKey(month)) {
//                todoMap.put(month, new ArrayList<Todo>());
//                months.add(month);
//            }
//            todoMap.get(month).add(todo);
//        }
//        return todoMap;
//    }

    @POST
    @Path("/add")
    public Viewable saveTodo(@FormParam("date") String todoDate,
                             @FormParam("notes") String notes) throws SQLException, ParseException {
        LocalDate date = LocalDate.parse(todoDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        TodoRepo.save(new Todo(0, -1, date, notes, ""));
        return todos();
    }

}
