package net.astechdesign.clients.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import net.astechdesign.clients.model.Address;
import net.astechdesign.clients.model.Contact;
import net.astechdesign.clients.model.Name;
import net.astechdesign.clients.model.Todo;
import net.astechdesign.clients.repo.ContactsRepo;
import org.glassfish.jersey.server.mvc.Viewable;
import org.joda.time.DateTime;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class ContactsResource {

    @GET
    @Path("/todos")
    public Viewable todos() {
        Map<String, Object> data = new HashMap<>();
        ArrayList<Todo> todos = Lists.newArrayList(
                new Todo(1, 11, "John Smith", new DateTime().toDate(), new DateTime().toDate(), "Notes")
        );
        data.put("todos", todos);
        return new Viewable("todos", data);
    }

    @GET
    @Path("/addtodo")
    public Viewable addTodo() {
        Map<String, Object> data = new HashMap<>();
        return new Viewable("newTodo", data);
    }

    @POST
    @Path("/addtodo")
    public Viewable newTodo() {
        Map<String, Object> data = new HashMap<>();
        return todos();
    }

    @GET
    @Path("/contacts")
    public Viewable contacts() throws SQLException {
        Map<String, Object> data = new HashMap<>();
        data.put("contacts", ContactsRepo.getContacts());
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
    public Viewable save(@FormParam("contactId") int id,
                     @FormParam("first") String firstName,
                     @FormParam("last") String lastName,
                     @FormParam("number") int houseNumber,
                     @FormParam("house") String houseName,
                     @FormParam("address1") String address1,
                     @FormParam("town") String town,
                     @FormParam("county") String county,
                     @FormParam("postcode") String postcode,
                     @FormParam("telephone") String tel) throws JsonProcessingException, SQLException {
        Name name = new Name(firstName, lastName);
        Address address = new Address(houseNumber, houseName, address1, town, county, postcode, tel);
        ContactsRepo.save(new Contact(id, name, address));
        return contacts();
    }
}
