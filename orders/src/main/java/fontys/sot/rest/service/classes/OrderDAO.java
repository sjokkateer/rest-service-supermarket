package fontys.sot.rest.service.classes;

import fontys.sot.rest.service.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    // Holds the collection of orders.
    List<Order> orders = new ArrayList<>();

    public Order getOrder(int orderNumber) {
        for (Order order: orders) {
            if (order.getNumber() == orderNumber) {
                return order;
            }
        }

        return null;
    }

    public Order createNewOrderFor(int userId) {
        return new Order(userId);
    }

    public void add(Order order) {
        orders.add(order);
    }
}
