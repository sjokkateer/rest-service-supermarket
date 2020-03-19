package fontys.sot.rest.client.classes;

import response.helpers.classes.Url;

/**
 * Class serves much like a factory for URLs to specific services.
 */
public class WebshopUrl extends Url {
    private static final String WEBSHOP = "webshop";

    private static String getFullEndpointUrl(String endpoint) {
        return BASE_URL + "/" + WEBSHOP + "/" + REST + "/" + endpoint;
    }
}
