package net.astechdesign.clients.resources;

import net.astechdesign.clients.model.product.Product;
import net.astechdesign.clients.model.product.ProductRepo;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Path("/products")
@Produces(MediaType.TEXT_HTML)
public class ProductRequests {

    @GET
    public Viewable products() throws SQLException {
        Map<String, Object> data = new HashMap<>();
        data.put("products", ProductRepo.products());
        return new Viewable("products", data);
    }

    @POST
    @Path("/add")
    public Viewable saveProduct(@FormParam("productId") int productId,
                     @FormParam("name") String name,
                     @FormParam("description") String description) throws SQLException {
        ProductRepo.save(new Product(0, productId, name, description));
        return products();
    }
}
