package response.helpers.classes;

/**
 * Class serves much like a factory for URLs to specific services.
 */
public class Url {
    protected static final String HOME = "http://localhost";
    protected static final int PORT = 8080;
    protected static final String REST = "rest";
    protected static final String BASE_URL = HOME + ":" + PORT;

    public static String createFor(Endpoint endPoint) {
        switch(endPoint) {
            case PRODUCTS:
                return getFullEndpointUrl("products");
            case USERS:
                return getFullEndpointUrl("users");
            case ORDERS:
                return getFullEndpointUrl("orders");
            default:
                throw new IllegalArgumentException("Impossible endpoint");
        }
    }

    private static String getFullEndpointUrl(String endpoint) {
        return BASE_URL + "/" + endpoint + "/" + REST;
    }
}
