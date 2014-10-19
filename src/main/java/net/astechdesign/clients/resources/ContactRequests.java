package net.astechdesign.clients.resources;

import net.astechdesign.clients.model.contact.Contact;
import net.astechdesign.clients.model.contact.ContactRepo;
import net.astechdesign.clients.model.contact.Telephone;
import net.astechdesign.clients.model.order.Order;
import net.astechdesign.clients.model.order.OrderRepo;
import net.astechdesign.clients.model.product.ProductRepo;
import net.astechdesign.clients.model.todo.Todo;
import net.astechdesign.clients.model.todo.TodoRepo;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Path("/contacts")
@Produces(MediaType.TEXT_HTML)
public class ContactRequests {

    @GET
    public Viewable contacts() throws SQLException {
        Map<String, Object> data = new HashMap<>();
        data.put("contacts", ContactRepo.get());
        return new Viewable("contacts", data);
    }

    @POST
    @Path("/action")
    public Viewable saveContact(@FormParam("contactId") int id,
                                @FormParam("name") String name,
                                @FormParam("address") String address,
                                @FormParam("postcode") String postcode,
                                @FormParam("telephone") Telephone tel,
                                @FormParam("action") String action) throws SQLException {
        String val = name + address + postcode + tel;
        if (action.equals("find") && val.trim().length() > 0) return findContacts(name, address, postcode, tel);
        if (action.equals("update") && val.trim().length() > 0) {
            ContactRepo.update(new Contact(id, name, address, postcode, tel));
            return contact(id);
        }
        ContactRepo.save(new Contact(id, name, address, postcode, tel));
        return contacts();
    }

    private Viewable findContacts(String name,
                                  String address,
                                  String postcode,
                                  Telephone tel) throws SQLException {
        Map<String, Object> data = new HashMap<>();
        data.put("contacts", ContactRepo.find(name, address, postcode, tel));
        return new Viewable("contacts", data);
    }

    @POST
    @Path("/todo")
    public Viewable saveTodo(@FormParam("contactId") int contactId,
                             @FormParam("date") Date date,
                             @FormParam("notes") String notes) throws SQLException {
        TodoRepo.save(new Todo(0, contactId, date, notes, ""));
        return contact(contactId);
    }

    @POST
    @Path("/order")
    public Viewable saveOrder(MultivaluedMap<String, String> formParams) throws SQLException, ParseException {
        int contactId = Integer.parseInt(formParams.getFirst("contactId"));
        List<String> productIds = formParams.get("productId");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (String id : productIds) {
            int productId = Integer.parseInt(id);
            Date deliveryDate = sdf.parse(formParams.getFirst("delivery_" + id));
            int amount = Integer.parseInt(formParams.getFirst("amount_" + id));
            String name = formParams.getFirst("name_" + id);
            OrderRepo.save(new Order(contactId, productId, deliveryDate, amount, "", "", new Date()));
            TodoRepo.save(new Todo(0, contactId, deliveryDate, "Delivery for: " + name, ""));
        }
        return contact(contactId);
    }

    @GET
    @Path("/{id}")
    public Viewable contact(@PathParam("id") int id) throws SQLException {
        Map<String, Object> data = new HashMap<>();
        data.put("contact", ContactRepo.get(id));
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM");

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
        data.put("todoMonths", months);

        Map<String, List<Order>> orderMap = new HashMap<>();
        months = new ArrayList<>();
        for (Order order : OrderRepo.orders(id)) {
            String month = sdf.format(order.deliveryDate);
            if (!orderMap.containsKey(month)) {
                orderMap.put(month, new ArrayList<Order>());
                months.add(month);
            }
            orderMap.get(month).add(order);
        }

        data.put("orders", orderMap);
        data.put("orderMonths", months);
        data.put("products", ProductRepo.products());
        return new Viewable("contact", data);
    }
}
