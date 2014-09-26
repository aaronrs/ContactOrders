package net.astechdesign.clients.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.astechdesign.clients.model.product.Product;
import net.astechdesign.clients.model.product.ProductRepo;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Path("/products")
public class ProductRequests {

    private ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable products() throws SQLException {
        Map<String, Object> data = new HashMap<>();
        data.put("products", ProductRepo.products());
        return new Viewable("products", data);
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public String list() throws Exception {
        return objectMapper.writeValueAsString(ProductRepo.products());
    }

    @GET
    @Path("/delete/{id}")
    @Produces(MediaType.TEXT_HTML)
    public Viewable delete(@PathParam("id") int productId) throws SQLException {
        ProductRepo.delete(productId);
        return products();
    }

    @POST
    @Path("/add")
    @Produces(MediaType.TEXT_HTML)
    public Viewable saveProduct(@FormParam("productId") int productId,
                     @FormParam("name") String name,
                     @FormParam("description") String description) throws SQLException {
        ProductRepo.save(new Product(0, productId, name, description));
        return products();
    }
}
