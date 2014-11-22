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
    public Viewable delete(@PathParam("id") int id) throws SQLException {
        ProductRepo.delete(id);
        return products();
    }

    @GET
    @Path("/edit/{id}")
    @Produces(MediaType.TEXT_HTML)
    public Viewable edit(@PathParam("id") int id) throws SQLException {
        Viewable data = products();
        Product product = ProductRepo.find(id);
        HashMap model = (HashMap) data.getModel();
        model.put("product",product);
        return data;
    }

    @POST
    @Path("/add")
    @Produces(MediaType.TEXT_HTML)
    public Viewable saveProduct(@FormParam("id") int id,
                                @FormParam("code") String productId,
                                @FormParam("name") String name,
                                @FormParam("description") String description) throws SQLException {
        if (name.length() != 0) {
            ProductRepo.save(new Product(id, productId, name.toUpperCase(), description));
        }
        return products();
    }
}
