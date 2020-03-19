package fontys.sot.rest.client.classes;

import response.helpers.classes.Endpoint;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RegistrationRequestResponseHandler extends RequestResponseHandler {
    public RegistrationRequestResponseHandler() {
        super(Endpoint.USERS);
    }

    public Response register(String username, char[] password, char[] confirmPassword) {
        Form form = new Form();
        form.param("name", username);
        form.param("password", String.valueOf(password));
        form.param("confirm", String.valueOf(confirmPassword));

        Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);

        Response response = getServiceTarget()
                .request()
                .post(entity);

        return response;
    }

}
