package net.astechdesign.clients.resources;

import net.astechdesign.clients.model.product.Product;
import net.astechdesign.clients.model.product.ProductRepo;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/products")
public class ProductRequests {

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
    public String list() throws SQLException {
        Map<String, Object> data = new HashMap<>();
        List<Product> products = ProductRepo.products();
        return "{[{\"id\":0,\"name\":\"Cheese\"}," +
                " {\"id\":1,\"name\":\"Paella\"}," +
                " {\"id\":2,\"name\":\"Fish\"}]}";
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
