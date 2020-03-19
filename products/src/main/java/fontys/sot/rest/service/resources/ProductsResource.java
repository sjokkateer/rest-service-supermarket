package fontys.sot.rest.service.resources;

import fontys.sot.rest.service.classes.ProductSorter;
import fontys.sot.rest.service.models.Product;
import fontys.sot.rest.service.classes.ProductDAO;
import response.helpers.classes.Description;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Singleton
@Path("")
public class ProductsResource {
    private static final String RESOURCE = "Product";
    private ProductDAO productDAO = new ProductDAO();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response all() {
        List<Product> products = productDAO.all();

        return Response.ok(products).build();
    }

    @GET @Path("sort")
    @Produces({MediaType.APPLICATION_JSON})
    public Response sort(@QueryParam("method") String method) {
        List<Product> products = productDAO.all();

        // Assume we extend this later to a category based filter followed
        // by perhaps a sort based on multiple parameters /products/{category}?sort=price
        // ProductFilterer filterer = ProductFilterer(method);
        // List<Product> filteredProducts = filterer.filter(products);

        ProductSorter sorter = new ProductSorter(method);
        List<Product> sortedProducts = sorter.sort(products);

        return Response.ok(sortedProducts).build();
    }

    @GET @Path("find")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@QueryParam("name") String name) {
        // Null and empty string will return the entire collection
        if (name == null) {
            name = "";
        }

        List<Product> products = productDAO.find(name);

        // Empty list or no contents response, in case a name was not found?
        return Response.ok(products).build();
    }

    @GET @Path("{productId}")
    public Response get(@PathParam("productId") int id) {
        Product target = productDAO.find(id);

        if (target == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Description.createNotFound(RESOURCE, "id", id))
                    .build();
        }

        return Response.ok(target).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Product product) {
        Product addedProduct = productDAO.add(product);

        if (addedProduct == null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(Description.create("Product with identical attributes already exists."))
                    .build();
        }

        return Response.status(Response.Status.CREATED)
                .entity(addedProduct)
                .build();
    }

    @PUT @Path("{productId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response update(
            @PathParam("productId") int id,
            @FormParam("name") String name,
            @FormParam("unit") String unit,
            @FormParam("quantity") int quantity,
            @FormParam("price") double price
    ) {
        Product targetProduct = productDAO.find(id);

        if (targetProduct == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Description.createNotFound(RESOURCE, "id", id))
                    .build();
        }

        targetProduct.update(name, unit, quantity, price);

        return Response.ok()
                .entity(Description.createSuccess(RESOURCE, id, "UPDATED"))
                .build();
    }

    @DELETE @Path("{productId}")
    public Response delete(@PathParam("productId") int id) {
        boolean productDeleted = productDAO.remove(id);

        if (productDeleted) {
            return Response.ok()
                    .entity(Description.createSuccess(RESOURCE, id, "DELETED"))
                    .build();
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity(Description.createNotFound(RESOURCE, "id", id))
                .build();
    }

    @POST @Path("discount/{productId}")
    public Response applyDiscount(
            @PathParam("productId") int id,
            @FormParam("percentage") int percentage
    ) {
        Product targetProduct = productDAO.find(id);

        if (targetProduct != null) {
            targetProduct.setDiscountPercentage(percentage);

            return Response.ok()
                    .entity(Description.create("Product with id (" + id + ") is now " + percentage + "% discounted."))
                    .build();
        }

        return Response
                .serverError()
                .entity(Description.create("Something unexpected happened"))
                .build();
    }
}