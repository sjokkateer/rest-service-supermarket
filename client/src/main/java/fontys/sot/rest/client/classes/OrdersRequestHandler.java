package fontys.sot.rest.client.classes;

import fontys.sot.rest.service.models.Order;
import response.helpers.classes.Endpoint;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class OrdersRequestHandler extends RequestResponseHandler {
    public OrdersRequestHandler() {
        super(Endpoint.ORDERS);
    }

    public Response get(int userId) {
        Form form = new Form();
        form.param("userId", String.valueOf(userId));

        Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);

        return getServiceTarget()
                .request()
                .post(entity);
    }

    public Response store(Order order) {
        Entity<Order> entity = Entity.entity(order, MediaType.APPLICATION_JSON);

        return getServiceTarget()
                .path(String.valueOf(order.getNumber()))
                .request()
                .post(entity);
    }
}
