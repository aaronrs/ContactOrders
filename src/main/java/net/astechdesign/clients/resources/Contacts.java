package net.astechdesign.clients.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.astechdesign.clients.model.contact.*;
import net.astechdesign.clients.model.order.Order;
import net.astechdesign.clients.model.order.OrderRepo;
import net.astechdesign.clients.model.product.Product;
import net.astechdesign.clients.model.product.ProductRepo;
import net.astechdesign.clients.model.todo.Todo;
import net.astechdesign.clients.model.todo.TodoRepo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Path("/contacts")
public class Contacts {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get() throws JsonProcessingException, SQLException {
        return objectMapper.writeValueAsString(ContactRepo.get());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getContactById(@PathParam("id") int id) throws JsonProcessingException, SQLException {
        return objectMapper.writeValueAsString(ContactRepo.get(id));
    }

    @GET
    @Path("/todo")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTodoList() throws JsonProcessingException, SQLException {
        return objectMapper.writeValueAsString(TodoRepo.todos());
    }

    @GET
    @Path("/{id}/todo")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTodoList(@PathParam("id") int contactId) throws JsonProcessingException, SQLException {
        return objectMapper.writeValueAsString(TodoRepo.todos());
    }

    @GET
    @Path("/{id}/orders")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrders(@PathParam("id") int id) throws JsonProcessingException, SQLException {
        return objectMapper.writeValueAsString(OrderRepo.orders(id));
    }

    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public String getProducts() throws JsonProcessingException, SQLException {
        return objectMapper.writeValueAsString(ProductRepo.products());
    }

    @POST
    @Path("/{id}/todo")
    public void saveTodo(@PathParam("id") int contactId,
                         @FormParam("start") String start,
                         @FormParam("end") String end,
                         @FormParam("notes") String notes) throws JsonProcessingException, SQLException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
        TodoRepo.save(new Todo(-1, contactId, Name.DEFAULT, sdf.parse(start), sdf.parse(end), notes));
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
                     @FormParam("telephone") Telephone tel) throws JsonProcessingException, SQLException {
        Name name = new Name(firstName, lastName);
        Address address = new Address(houseNumber, houseName, address1, town, county, postcode);
        ContactRepo.save(new Contact(id, name, address, tel));
    }

    @POST
    @Path("/{id}/orders")
    public void saveOrder(@PathParam("id") int contactId,
                     @FormParam("productId") int productId,
                     @FormParam("year") int year,
                     @FormParam("month") int month,
                     @FormParam("day") int day,
                     @FormParam("amount") int amount) throws JsonProcessingException, SQLException {
        OrderRepo.save(contactId, new Order(contactId, productId, year, month, day, amount, null, null, null));
    }

    public void addProduct(int productId, int categoryId, String name, String description) throws SQLException {
        Product product = new Product(productId, name, description);
        ProductRepo.save(product);
    }
}
