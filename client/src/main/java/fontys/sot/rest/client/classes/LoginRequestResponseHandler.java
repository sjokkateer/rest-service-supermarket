package fontys.sot.rest.client.classes;

import fontys.sot.rest.service.models.User;
import response.helpers.classes.Endpoint;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class LoginRequestResponseHandler extends RequestResponseHandler {
    public LoginRequestResponseHandler() {
        super(Endpoint.USERS);
    }

    public User login(String username, String password) {
        Form form = new Form();
        form.param("name", username);
        form.param("password", password);

        Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);

        Response response = getServiceTarget()
                .path("/login")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .post(entity);

        if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
            return null;
        }

        User user = response.readEntity(User.class);
        return user;
    }
}
