package fontys.sot.rest.service.models;

import java.util.Objects;

public class Product implements Cloneable {
    private int id;
    private String name;
    private String unit;
    private int quantity;
    private double price;
    private int discountPercentage = 0;

    public Product() {
        super();
    }

    public Product(String name, String unit, int quantity, double price) {
        this();
        setName(name);
        setUnit(unit);
        setQuantity(quantity);
        setPrice(price);
    }

    public Product(int id, String name, String unit, int quantity, double price) {
        this(name, unit, quantity, price);

        setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnit() {
        return upperCaseFirst(unit);
    }

    public void setUnit(String unit) {
        if (isNotNullAndNotEmpty(unit)) {
            this.unit = unit.toLowerCase();
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        }
    }

    public String getName() {
        return upperCaseFirst(name);
    }

    private String upperCaseFirst(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    public void setName(String name) {
        if (isNotNullAndNotEmpty(name)) {
            this.name = name.toLowerCase();
        }
    }

    private boolean isNotNullAndNotEmpty(String value) {
        return value != null && !value.equals("");
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price > 0) {
            this.price = price;
        }
    }

    public double getDiscountedPrice() {
        return getPrice() * ((100 - getDiscountPercentage()) / 100.0);
    }

    public void setDiscountPercentage(int percentage) {
        if (percentage >= 0 && percentage <= 100) {
            discountPercentage = percentage;
        }
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public boolean isDiscounted() {
        return getDiscountPercentage() > 0;
    }

    public void update(String name, String unit, int quantity, double price) {
        setName(name);
        setUnit(unit);
        setQuantity(quantity);
        setPrice(price);
    }

    @Override
    public Object clone() {
        Product p = new Product(id, name, unit, quantity, price);
        p.setDiscountPercentage(discountPercentage);

        return p;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof Product) {
            Product p = (Product) o;

            return p.getName().equals(getName())
                    && p.getUnit().equals(getUnit())
                    && p.getQuantity() == getQuantity()
                    && p.getPrice() == getPrice()
                    && p.getDiscountPercentage() == getDiscountPercentage();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, unit, quantity, price);
    }

    @Override
    public String toString() {
        return getId() + ": " + getName();
    }
}
