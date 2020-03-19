package fontys.sot.rest.client.classes;

import fontys.sot.rest.service.models.Product;
import response.helpers.classes.Endpoint;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ProductsRequestHandler extends RequestResponseHandler {
    public ProductsRequestHandler() {
        super(Endpoint.PRODUCTS);
    }

    public Response getAll(String sortMethod) {
        return getServiceTarget()
                .path("sort")
                .queryParam("method", sortMethod)
                .request()
                .get();
    }

    public Response create(String name, String unit, int quantity, double price) {
        Product product = new Product(name, unit, quantity, price);
        Entity<Product> entity = Entity.entity(product, MediaType.APPLICATION_JSON);

        return getServiceTarget()
                .request()
                .post(entity);
    }

    public Response delete(int productId) {
        return getServiceTarget()
                .path(String.valueOf(productId))
                .request()
                .delete();
    }

    public Response update(int id, String name, String unit, int quantity, double price) {
        Form form = new Form();
        form.param("name", name);
        form.param("unit", unit);
        form.param("quantity", String.valueOf(quantity));
        form.param("price", String.valueOf(price));

        Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);

        return getServiceTarget()
                .path(String.valueOf(id))
                .request()
                .put(entity);
    }

    public Response find(String searchName) {
        return getServiceTarget()
                .path("find")
                .queryParam("name", searchName)
                .request()
                .get();
    }

    public Response setDiscount(int productId, int discountPercentage) {
        Form form = new Form();
        form.param("percentage", String.valueOf(discountPercentage));

        Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);

        return getServiceTarget()
                .path("discount/" + productId)
                .request()
                .post(entity);
    }
}
