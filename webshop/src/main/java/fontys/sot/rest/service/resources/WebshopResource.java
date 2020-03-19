package fontys.sot.rest.service.resources;

import fontys.sot.rest.service.classes.OrdersRequestResponseHandler;
import fontys.sot.rest.service.classes.ProductsRequestResponseHandler;
import fontys.sot.rest.service.classes.UsersRequestResponseHandler;
import fontys.sot.rest.service.models.Order;
import fontys.sot.rest.service.models.Product;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("")
public class WebshopResource {
    private static final String PRODUCTS = "products";
    private static final String USERS = "users";
    private static final String ORDERS = "orders";

    // Create a client to make calls to the other services
    ProductsRequestResponseHandler prrh;
    UsersRequestResponseHandler urrh;
    OrdersRequestResponseHandler orrh;

    public WebshopResource() {
       prrh = new ProductsRequestResponseHandler();
       urrh = new UsersRequestResponseHandler();
       orrh = new OrdersRequestResponseHandler();
    }

    @GET
    @Path(PRODUCTS)
    public Response allProducts() {
        return prrh.all();
    }

    @POST @Path(PRODUCTS)
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createProduct(Product product) {
        return prrh.create(product);
    }

    @GET @Path(PRODUCTS + "/" + ProductsRequestResponseHandler.SORT)
    public Response productsSorted(@QueryParam("method") String method) {
        return prrh.sort(method);
    }

    @GET @Path(PRODUCTS + "/" + ProductsRequestResponseHandler.FIND)
    public Response findProduct(@QueryParam("name") String name) {
        return prrh.find(name);
    }

    @GET @Path(PRODUCTS + "/{id}")
    public Response getProduct(@PathParam("id") int id) {
        return prrh.get(id);
    }

    @PUT @Path(PRODUCTS + "/{id}")
    public Response updateProduct(
            @PathParam("id") int id,
            @FormParam("name") String name,
            @FormParam("unit") String unit,
            @FormParam("quantity") int quantity,
            @FormParam("price") double price
    ) {
        Form form = new Form();
        form.param("name", name);
        form.param("unit", unit);
        form.param("quantity", String.valueOf(quantity));
        form.param("price", String.valueOf(price));

        return prrh.update(id, form);
    }

    @DELETE @Path(PRODUCTS + "/{id}")
    public Response deleteProduct(@PathParam("id") int id) {
        return prrh.delete(id);
    }

    @POST @Path(PRODUCTS + "/" + ProductsRequestResponseHandler.DISCOUNT + "/{id}")
    public Response discount(
            @PathParam("id") int id,
            @FormParam("percentage") int percentage
    ) {
        Form form = new Form();
        form.param("percentage", String.valueOf(percentage));

        return prrh.discount(id, form);
    }

    @GET @Path(USERS)
    public Response allUsers() {
        return urrh.all();
    }

    @POST @Path(USERS + "/login")
    public Response login(
        @FormParam("name") String name,
        @FormParam("password") String password
    ) {
        Form form = new Form();
        form.param("name", name);
        form.param("password", password);

        return urrh.login(form);
    }

    @POST @Path(USERS)
    public Response createUser(
            @FormParam("name") String name,
            @FormParam("password") String password,
            @FormParam("confirm") String confirmPassword
    ) {
        Form form = new Form();
        form.param("name", name);
        form.param("password", password);
        form.param("confirm", confirmPassword);

        return urrh.create(form);
    }

    @GET @Path(ORDERS + "/{orderNumber}")
    public Response getOrder(@PathParam("orderNumber") int orderNumber) {
        return orrh.get(orderNumber);
    }

    @POST @Path(ORDERS)
    public Response getOpenOrderOrCreateNew(@FormParam("userId") int userId) {
        Response response = urrh.get(userId);

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return response;
        }

        return orrh.createOrder(userId);
    }

    @POST @Path(ORDERS + "/{orderNumber}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response storeOrder(@PathParam("orderNumber") int orderNumber, Order order) {
        return orrh.store(orderNumber, order);
    }
}
