package fontys.sot.rest.service.classes;

import fontys.sot.rest.service.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static int productId = 1;

    private List<Product> products = new ArrayList<>();

    // Products are equal if they have the same name (to lower), unit and quantity.
    public ProductDAO() {
        add(new Product("Unox erwtensoep stevig", "ml", 800, 1.92));
        add(new Product("Caesar salade", "gram", 250, 2.89));
        add(new Product("Bapao rundvlees vers", "piece(s)", 1, 0.88));
        add(new Product("Zaanse metchup", "gram", 170, 0.81));
        add(new Product("Roze zalm", "gram", 213, 2.75));
    }

    /**
     * Returns the collection of products.
     *
     * @return List<Product> products
     */
    public List<Product> all() {
        return products;
    }

    /**
     * Finds all products that contain the given name in it.
     *
     * @param name
     * @return List<Product> result
     */
    public List<Product> find(String name) {
        List<Product> result = new ArrayList<>();

        for (Product p: products) {
            String productName = p.getName().toLowerCase();
            String searchName = name.toLowerCase();

            if (productName.contains(searchName)) {
                result.add(p);
            }
        }

        return result;
    }

    /**
     * Attempts to remove the product by given id.
     *
     * Returning the deleted product or null whether
     * the product was found or not.
     * @param id
     * @return boolean isRemoved
     */
    public boolean remove(int id) {
        boolean isRemoved = false;
        Product target = find(id);

        if (target != null) {
            products.remove(target);
            isRemoved = true;
        }

        return isRemoved;
    }

    /**
     * Helper method that iterates the collection
     * of products trying to find a product with
     * id equal to the given id.
     *
     * @param id
     * @return Product p || null
     */
    public Product find(int id) {
        for (Product p: products) {
            if (p.getId() == id) {
                return p;
            }
        }

        return null;
    }

    /**
     * Checks if an equivalent product does not exist in
     * the collection of products.
     *
     * If there does not exist one, it gives a new unique id
     * to the product, otherwise it returns null.
     *
     * @param product
     * @return Product product || null
     */
    public Product add(Product product) {
        if (exists(product)) {
            return null;
        }

        product.setId(productId++);
        products.add(product);

        return product;
    }

    /**
     * Helper method that tests if a product
     * already exists in the collection of products.
     *
     * @param product
     * @return
     */
    private boolean exists(Product product) {
        for (Product p: products) {
            if (product.equals(p)) {
                return true;
            }
        }

        return false;
    }
}
