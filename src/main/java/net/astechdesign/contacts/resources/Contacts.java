package net.astechdesign.contacts.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.astechdesign.contacts.model.Address;
import net.astechdesign.contacts.model.Contact;
import net.astechdesign.contacts.model.Name;
import net.astechdesign.contacts.model.Order;
import net.astechdesign.contacts.repo.ContactsRepo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/contacts")
public class Contacts {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get() throws JsonProcessingException, SQLException {
        return objectMapper.writeValueAsString(ContactsRepo.getContacts());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getContactById(@PathParam("id") int id) throws JsonProcessingException, SQLException {
        return objectMapper.writeValueAsString(ContactsRepo.getContact(id));
    }

    @GET
    @Path("/{id}/orders")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrders(@PathParam("id") int id) throws JsonProcessingException, SQLException {
        return objectMapper.writeValueAsString(ContactsRepo.orders(id));
    }

    @POST
    public void save(@FormParam("contactId") int id,
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
    }

    @POST
    @Path("/{id}/orders")
    public void saveOrder(@PathParam("id") int contactId,
                     @FormParam("productId") int productId,
                     @FormParam("year") int year,
                     @FormParam("month") int month,
                     @FormParam("day") int day,
                     @FormParam("amount") int amount) throws JsonProcessingException, SQLException {
        ContactsRepo.save(contactId, new Order(contactId, year, month, day, productId, amount, null, null, null));
    }

    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public String getProducts() throws JsonProcessingException, SQLException {
        return objectMapper.writeValueAsString(ContactsRepo.products());
    }

    @GET
    @Path("/categories")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCategories() throws JsonProcessingException, SQLException {
        return objectMapper.writeValueAsString(ContactsRepo.categories());
    }
}
