package fontys.sot.rest.service.classes;

import fontys.sot.rest.service.enums.ProductSortMethod;
import fontys.sot.rest.service.models.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static fontys.sot.rest.service.enums.ProductSortMethod.ID;

public class ProductSorter {
    private ProductSortMethod method;

    public ProductSorter(String method) {
        this.method = getSortMethod(method);
    }

    public ProductSortMethod getSortMethod(String method) {
        for (ProductSortMethod psm: ProductSortMethod.values()) {
            String methodName = method.toUpperCase();

            if (psm.name().equals(methodName)) {
                return ProductSortMethod.valueOf(methodName);
            }
        }

        return ID;
    }

    public List<Product> sort(List<Product> products) {
        List<Product> result = new ArrayList<>(products);

        result.sort(getCorrespondingSortMethod());

        return result;
    }

    private Comparator<Product> getCorrespondingSortMethod() {
        switch (this.method) {
            case PRICE:
                return Comparator.comparing(Product::getPrice);
            case DISCOUNT:
                return Comparator.comparing(Product::getDiscountPercentage, Collections.reverseOrder());
            default:
                return Comparator.comparing(Product::getId);
        }
    }
}


