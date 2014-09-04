package net.astechdesign.clients.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.astechdesign.clients.model.contact.*;
import net.astechdesign.clients.model.order.Order;
import net.astechdesign.clients.model.order.OrderRepo;
import net.astechdesign.clients.model.product.ProductRepo;
import net.astechdesign.clients.model.todo.Todo;
import net.astechdesign.clients.model.todo.TodoRepo;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
                                @FormParam("first") String firstName,
                                @FormParam("last") String lastName,
                                @FormParam("address") String address,
                                @FormParam("postcode") String postcode,
                                @FormParam("telephone") Telephone tel,
                                @FormParam("telephone") String action) throws SQLException {
        if (action.equals("find")) return findContacts(firstName, lastName, address, postcode, tel);
        ContactRepo.save(new Contact(id, firstName, lastName, address, postcode, tel));
        return contacts();
    }

    private Viewable findContacts(String firstName,
                                  String lastName,
                                  String address,
                                  String postcode,
                                  Telephone tel) throws SQLException {
        Map<String, Object> data = new HashMap<>();
        data.put("contacts", ContactRepo.find(firstName, lastName, address, postcode, tel));
        return new Viewable("contacts", data);
    }

    @POST
    @Path("/todo")
    public Viewable saveTodo(@FormParam("contactId") int contactId,
                             @FormParam("date") Date date,
                             @FormParam("notes") String notes) throws SQLException {
        TodoRepo.save(new Todo(0, contactId, date, notes));
        return contact(contactId);
    }

    @POST
    @Path("/order")
    public Viewable saveOrder(@FormParam("contactId") int contactId,
                     @FormParam("productId") int productId,
                     @FormParam("date") Date date,
                     @FormParam("amount") int amount) throws SQLException {
        OrderRepo.save(new Order(contactId, productId, date, amount, "", ""));
        return contact(contactId);
    }

    @GET
    @Path("/{id}")
    public Viewable contact(@PathParam("id") int id) throws SQLException {
        Map<String, Object> data = new HashMap<>();
        data.put("contact", ContactRepo.get(id));
        data.put("todos", TodoRepo.todos(id));
        data.put("orders", OrderRepo.orders(id));
        data.put("products", ProductRepo.products());
        return new Viewable("contact", data);
    }
}
