package fontys.sot.rest.service.classes;

import response.helpers.classes.Endpoint;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * A specific users' service request response handler
 * Should implement all forwarding calls to the concrete endpoint.
 */
public class UsersRequestResponseHandler extends RequestResponseHandler {
    public UsersRequestResponseHandler() {
        super(Endpoint.USERS);
    }

    public Response all() {
        return getServiceTarget()
                .request()
                .get();
    }

    public Response create(Form form) {
        Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);

        return getServiceTarget()
                .request()
                .post(entity);
    }

    public Response get(int userId) {
        return getServiceTarget()
                .path(String.valueOf(userId))
                .request()
                .get();
    }

    public Response login(Form form) {
        Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);

        return getServiceTarget()
                .path("/login")
                .request()
                .post(entity);
    }
}
