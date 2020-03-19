package fontys.sot.rest.service.resources;

import fontys.sot.rest.service.classes.UserDAO;
import fontys.sot.rest.service.classes.validators.UserValidator;
import fontys.sot.rest.service.models.User;
import response.helpers.classes.Description;
import response.helpers.classes.Error;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("")
public class UsersResource {
    private static final String RESOURCE = "User";
    private UserDAO userDAO = new UserDAO();

    @GET
    public Response all() {
        return Response.ok(userDAO.getUsers()).build();
    }

    @GET @Path("/{id}")
    public Response get(@PathParam("id") int id) {
        User user = userDAO.find(id);

        if (user == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(Description.createNotFound(RESOURCE, "id", id))
                    .build();
        }

        return Response.ok(user).build();
    }

    @POST @Path("/login")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public Response login(
            @FormParam("name") String name,
            @FormParam("password") String password
    ) {
        User user = userDAO.authenticate(name, password);

        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return Response.ok(user).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public Response create(
            @FormParam("name") String name,
            @FormParam("password") String password,
            @FormParam("confirm") String confirmPassword
    ) {
        UserValidator validator = new UserValidator(name, password, confirmPassword);

        if (validator.isNotValid()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Error(validator.getErrors()))
                    .build();
        }

        User user = new User(name, password);
        boolean userAdded = userDAO.add(user);

        if (!userAdded) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(Description.create("Username: '" + user.getName() + "' is already taken!"))
                    .build();
        }

        return Response.ok(user).build();
    }
}
