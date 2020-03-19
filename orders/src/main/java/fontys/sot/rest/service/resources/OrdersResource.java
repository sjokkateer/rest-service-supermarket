package fontys.sot.rest.service.resources;

import fontys.sot.rest.service.classes.OrderDAO;
import fontys.sot.rest.service.models.Order;
import response.helpers.classes.Description;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("")
public class OrdersResource {
    private static final String RESOURCE = "Order";

    private OrderDAO orderDAO = new OrderDAO();

    @GET @Path("{orderNumber}")
    public Response get(@PathParam("orderNumber") int orderNumber) {
        Order order = orderDAO.getOrder(orderNumber);

        if (order == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(Description.createNotFound(RESOURCE, "number", orderNumber))
                    .build();
        }

        return Response.ok(order).build();
    }

    @POST
    public Response createNewOrder(@FormParam("userId") int userId) {
        return Response.ok(orderDAO.createNewOrderFor(userId)).build();
    }

    @POST @Path("{orderNumber}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response store(Order order) {
        orderDAO.add(order);

        return Response
                .ok(Description.createSuccess(RESOURCE, order.getNumber(), "STORED"))
                .build();
    }
}
