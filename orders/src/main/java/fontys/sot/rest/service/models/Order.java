package fontys.sot.rest.service.models;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int orderNumber = 0;

    private int number;
    private int userId;
    List<LineItem> lineItems;

    public Order() {
        super();
    }

    public Order(int userId) {
        this();

        number = ++orderNumber;

        this.userId = userId;
        lineItems = new ArrayList<>();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotal() {
        double sum = 0.0;

        for (LineItem lineItem: lineItems) {
            sum += lineItem.getTotal();
        }

        return sum;
    }

    public void addLineItem(LineItem lineItem) {
        for (LineItem li: lineItems) {
            if (li.getProduct().equals(lineItem.getProduct())) {
                li.addQuantity(lineItem.getQuantity());
                return;
            }
        }

        lineItems.add(lineItem);
    }

    public boolean isEmpty() {
        return lineItems.size() == 0;
    }

    public void remove(LineItem lineItem) {
        lineItems.remove(lineItem);
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }
}
