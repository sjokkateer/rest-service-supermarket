package fontys.sot.rest.service.classes;

import fontys.sot.rest.service.models.Product;
import response.helpers.classes.Endpoint;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * A specific products' service request response handler
 * Should implement all forwarding calls to the concrete endpoint.
 */
public class ProductsRequestResponseHandler extends RequestResponseHandler {
    public static final String SORT = "sort";
    public static final String FIND = "find";
    public static final String DISCOUNT = "discount";

    public ProductsRequestResponseHandler() {
        super(Endpoint.PRODUCTS);
    }

    public Response all()
    {
        return getServiceTarget()
                .request()
                .get();
    }

    public Response create(Product product) {
        Entity<Product> entity = Entity.entity(product, MediaType.APPLICATION_JSON);

        return getServiceTarget()
                .request(MediaType.APPLICATION_JSON)
                .post(entity);
    }

    public Response sort(String method) {
        return getServiceTarget()
                .queryParam("method", method)
                .path(SORT)
                .request()
                .get();
    }

    public Response find(String name) {
        return getServiceTarget()
                .queryParam("name", name)
                .path(FIND)
                .request()
                .get();
    }

    public Response get(int id) {
        return getServiceTarget()
                .path(String.valueOf(id))
                .request()
                .get();
    }

    public Response update(int id, Form form) {
        Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);

        return getServiceTarget()
                .path(String.valueOf(id))
                .request()
                .put(entity);
    }

    public Response delete(int id) {
        return getServiceTarget()
                .path(String.valueOf(id))
                .request()
                .delete();
    }

    public Response discount(int id, Form form) {
        Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);

        return getServiceTarget()
                .path(DISCOUNT + "/" + id)
                .request()
                .post(entity);
    }
}
