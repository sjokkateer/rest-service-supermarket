package fontys.sot.rest.service.models;

public class LineItem {
    private Product product;
    private int quantity;

    public LineItem() {
        super();
    }

    public LineItem(Product product, int quantity) {
        this();

        this.product = product;
        this.quantity = quantity;
    }

    public void addQuantity(int quantity) {
        int result = getQuantity() + quantity;

        if (result < 0) {
            result = 0;
        }

        this.quantity = result;
    }

    public double getTotal() {
        return product.getDiscountedPrice() * getQuantity();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return product.getName() + ", " + getQuantity() + String.format(", total: € %.2f", getTotal());
    }
}
