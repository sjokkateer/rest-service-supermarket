package fontys.sot.rest.client.classes;

import org.glassfish.jersey.client.ClientConfig;
import response.helpers.classes.Endpoint;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * An abstract base class that sets up the configuration to gracefully
 * dispatch requests and return responses to an endpoint.
 *
 * A specific endpoint should extend this base class and pass
 * the corresponding endpoint to this base class.
 */
abstract public class RequestResponseHandler {
    private Client client;
    private WebTarget serviceTarget;

    public RequestResponseHandler(Endpoint endpoint) {
        ClientConfig config = new ClientConfig();
        client = ClientBuilder.newClient(config);

        serviceTarget = createServiceTarget(endpoint);
    }

    private WebTarget createServiceTarget(Endpoint endPoint) {
        String serviceUrl = WebshopUrl.createFor(endPoint);
        URI baseUri = UriBuilder.fromUri(serviceUrl).build();

        return client.target(baseUri);
    }

    public WebTarget getServiceTarget() {
        return serviceTarget;
    }
}
