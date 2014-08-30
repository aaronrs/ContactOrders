package net.astechdesign.clients.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.astechdesign.clients.model.contact.*;
import net.astechdesign.clients.model.todo.Todo;
import net.astechdesign.clients.model.todo.TodoRepo;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.*;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class ContactsResource {

    @GET
    @Path("/todos")
    public Viewable todos() throws SQLException {
        Map<String, Object> data = new HashMap<>();
        data.put("todos", TodoRepo.todos());
        return new Viewable("todos", data);
    }

    @GET
    @Path("/addtodo")
    public Viewable addTodo() throws SQLException {
        Map<String, Object> data = new HashMap<>();
        data.put("contacts", ContactRepo.get());
        return new Viewable("newTodo", data);
    }

    @POST
    @Path("/addtodo")
    public Viewable saveTodo(@FormParam("contactId") int contactId,
                             @FormParam("start") Date start,
                             @FormParam("end") Date end,
                             @FormParam("notes") String notes) throws JsonProcessingException, SQLException {
        TodoRepo.save(new Todo(0, contactId, null, start, end, notes));
        return todos();
    }

    @GET
    @Path("/contact")
    public Viewable contact(@QueryParam("id") int id) throws SQLException {
        Map<String, Object> data = new HashMap<>();
        data.put("contact", ContactRepo.get(id));
        return new Viewable("contact", data);
    }

    @GET
    @Path("/contacts")
    public Viewable contacts() throws SQLException {
        Map<String, Object> data = new HashMap<>();
        data.put("contacts", ContactRepo.get());
        return new Viewable("contacts", data);
    }

    @GET
    @Path("/addcontact")
    public Viewable addContact() throws SQLException {
        Map<String, Object> data = new HashMap<>();
        return new Viewable("newContact", data);
    }

    @POST
    @Path("/addcontact")
    public Viewable saveContact(@FormParam("contactId") int id,
                     @FormParam("first") String firstName,
                     @FormParam("last") String lastName,
                     @FormParam("number") int houseNumber,
                     @FormParam("house") String houseName,
                     @FormParam("address1") String address1,
                     @FormParam("town") String town,
                     @FormParam("county") String county,
                     @FormParam("postcode") String postcode,
                     @FormParam("telephone") Telephone tel) throws JsonProcessingException, SQLException {
        Name name = new Name(firstName, lastName);
        Address address = new Address(houseNumber, houseName, address1, town, county, postcode);
        ContactRepo.save(new Contact(id, name, address, tel));
        return contacts();
    }

    @POST
    @Path("/searchcontact")
    public Viewable search(@FormParam("name") String name) throws SQLException {
        Map<String, Object> data = new HashMap<>();
        data.put("contact", ContactRepo.find(name));
        return new Viewable("contact", data);
    }


}
