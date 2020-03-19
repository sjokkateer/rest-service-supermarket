package fontys.sot.rest.client;

import fontys.sot.rest.client.classes.OrdersRequestHandler;
import fontys.sot.rest.client.classes.ProductsRequestHandler;
import fontys.sot.rest.service.enums.Role;
import fontys.sot.rest.service.models.LineItem;
import fontys.sot.rest.service.models.Order;
import fontys.sot.rest.service.models.Product;
import fontys.sot.rest.service.models.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class OrderApplication extends JFrame {
    private JPanel shopPanel;
    private JTabbedPane shopTabPane;
    private JPanel orderPanel;
    private JPanel managePanel;
    private JList productList;
    private JButton refreshBtn;
    private JLabel nameLb;
    private JLabel unitLb;
    private JLabel priceLb;
    private JLabel discountLb;
    private JRadioButton defaultSortRbtn;
    private JRadioButton discountSortRbtn;
    private JRadioButton priceSortRbtn;
    private JTextField createNameTextField;
    private JLabel createNameLb;
    private JTextField unitTextField;
    private JLabel createUnitLb;
    private JSpinner quantitySpinner;
    private JLabel createQuantityLb;
    private JTextField priceTextField;
    private JLabel createPriceLb;
    private JButton createBtn;
    private JLabel createOrUpdateProductLb;
    private JList createOrUpdateList;
    private JLabel createFeedbackLb;
    private JButton updateBtn;
    private JButton deleteBtn;
    private JLabel priceFeedbackLb;
    private JLabel quantityFeedbackLb;
    private JLabel unitFeedbackLb;
    private JLabel nameFeedbackLb;
    private JList orderLineList;
    private JPanel createOrderPanel;
    private JButton removeLineItemBtn;
    private JButton orderBtn;
    private JSpinner lineItemSpinner;
    private JButton addProductToOrderBtn;
    private JLabel nrSelectedProductsLb;
    private JLabel orderOverviewLb;
    private JLabel nrOfLineItemsFeedbackLb;
    private JLabel orderTotalLb;
    private JLabel removeLineItemFeedbackLb;
    private JLabel productInformationLb;
    private JLabel orderCartFeedbackLb;
    private JTextField searchTextField;
    private JButton searchBtn;
    private JLabel searchNameLb;
    private JLabel searchFeedbackLb;
    private JLabel sortByLb;
    private JLabel productsLb;
    private JSpinner discountSpinner;
    private JButton setDiscountBtn;
    private JLabel applyDiscountLb;
    private JLabel discountFeedbackLb;
    private JLabel createDiscountPriceLb;
    private JLabel createDiscountedPriceValueLb;

    private static int MANAGE_TAB_INDEX = 1;

    private User user;
    private ProductsRequestHandler prh;
    private OrdersRequestHandler orh;
    private Product selectedProduct;
    private Order order;

    private boolean validProductDetails;

    public OrderApplication(User user) {
        this("SHOP");
        this.user = user;

        orh = new OrdersRequestHandler();
        getNewOrder();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(shopPanel);
        pack();

        if (isNotManager(this.user)) {
            shopTabPane.setEnabledAt(MANAGE_TAB_INDEX, false);
            shopTabPane.remove(MANAGE_TAB_INDEX);
        }

        addProductToOrderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product selected = (Product) productList.getSelectedValue();

                if (selected != null) {
                    int quantity = (Integer) lineItemSpinner.getValue();
                    LineItem li = new LineItem(selected, quantity);
                    order.addLineItem(li);

                    updateOrderInfo();

                    clearLabel(nrOfLineItemsFeedbackLb);
                } else {
                    nrOfLineItemsFeedbackLb.setText("Please select a product from the overview!");
                    nrOfLineItemsFeedbackLb.setForeground(Color.RED);
                }
            }
        });

        removeLineItemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LineItem selectedLi = (LineItem) orderLineList.getSelectedValue();

                if (selectedLi != null) {
                    order.remove(selectedLi);
                    updateOrderInfo();

                    clearLabel(removeLineItemFeedbackLb);
                } else {
                    removeLineItemFeedbackLb.setText("Please select a line item from your cart");
                    removeLineItemFeedbackLb.setForeground(Color.RED);
                }
            }
        });

        orderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (order.isEmpty()) {
                    orderCartFeedbackLb.setText("Make sure to add products before ordering!");
                    orderCartFeedbackLb.setForeground(Color.RED);
                } else {
                    Response response = orh.store(order);

                    if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                        orderCartFeedbackLb.setText("Successfully placed order with number: " + order.getNumber());
                        orderCartFeedbackLb.setForeground(Color.GREEN);

                        getNewOrder();
                        updateOrderInfo();
                    } else {
                        orderCartFeedbackLb.setText("Something went wrong!");
                        orderCartFeedbackLb.setForeground(Color.RED);
                    }
                }
            }
        });

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchName = searchTextField.getText();

                if (searchName != null) {
                    Response response = prh.find(searchName);

                    if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                        clearLabel(searchFeedbackLb);

                        GenericType<List<Product>> genericType = new GenericType<>() {
                        };
                        List<Product> matches = response.readEntity(genericType);

                        productList.setListData(matches.toArray());
                    } else {
                        searchFeedbackLb.setText("Something went wrong when trying to get search matches");
                        searchFeedbackLb.setForeground(Color.RED);
                    }
                } else {
                    searchFeedbackLb.setText("Please enter text to search");
                    searchFeedbackLb.setForeground(Color.RED);
                }
            }
        });

        setDiscountBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearLabel(discountFeedbackLb);
                Product selectedProduct = (Product) createOrUpdateList.getSelectedValue();

                if (selectedProduct != null) {
                    int discountPercentage = (Integer) discountSpinner.getValue();
                    prh.setDiscount(selectedProduct.getId(), discountPercentage);

                    refreshProducts();
                    discountSpinner.setValue(0);
                } else {
                    discountFeedbackLb.setText("Please select a product");
                    discountFeedbackLb.setForeground(Color.RED);
                }
            }
        });
    }

    private void updateOrderInfo() {
        orderLineList.setListData(order.getLineItems().toArray());
        orderTotalLb.setText(String.format("Order total: € %.2f", order.getTotal()));
    }

    public OrderApplication(String title) {
        super(title);

        prh = new ProductsRequestHandler();
//        $$$setupUI$$$();
        refreshProducts();

        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshProducts();
            }
        });

        productList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedProduct = (Product) productList.getSelectedValue();

                if (selectedProduct != null) {
                    setProductDetails(selectedProduct);
                } else {
                    clearProductDetails();
                }
            }
        });

        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product product = createProduct();

                if (product != null) {
                    clearInputs();
                    refreshProducts();
                }
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product selected = (Product) createOrUpdateList.getSelectedValue();

                if (selected != null) {
                    Response response = prh.delete(selected.getId());

                    if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                        createFeedbackLb.setText("Successfully deleted: " + selected.getName());
                        createFeedbackLb.setForeground(Color.GREEN);

                        refreshProducts();
                    } else if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                        createFeedbackLb.setText("Could not find: " + selected.getName());
                        createFeedbackLb.setForeground(Color.GREEN);
                    } else {
                        createFeedbackLb.setText("Something went wrong when trying to delete the selected product.");
                        createFeedbackLb.setForeground(Color.RED);
                    }
                }
            }
        });

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product selected = (Product) createOrUpdateList.getSelectedValue();

                int id = selected.getId();
                String name = createNameTextField.getText();
                String unit = unitTextField.getText();
                int quantity = (Integer) quantitySpinner.getValue();
                double price = 0.0;

                try {
                    price = Double.parseDouble(priceTextField.getText());
                } catch (NumberFormatException ex) {
                }

                Response response = prh.update(id, name, unit, quantity, price);

                if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                    createFeedbackLb.setText("Successfully updated: " + selected.getId());
                    createFeedbackLb.setForeground(Color.GREEN);

                    refreshProducts();
                } else if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                    createFeedbackLb.setText("Could not find: " + selected.getName());
                    createFeedbackLb.setForeground(Color.GREEN);
                } else {
                    createFeedbackLb.setText("Something went wrong when trying to delete the selected product.");
                    createFeedbackLb.setForeground(Color.RED);
                }
            }
        });

        createOrUpdateList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                clearFeedbackLabels();

                Product selected = (Product) createOrUpdateList.getSelectedValue();

                if (selected != null) {
                    createNameTextField.setText(selected.getName());
                    unitTextField.setText(selected.getUnit());
                    quantitySpinner.setValue(selected.getQuantity());
                    priceTextField.setText(String.valueOf(selected.getPrice()));
                    discountSpinner.setValue(selected.getDiscountPercentage());
                    createDiscountedPriceValueLb.setText(String.format("€ %.2f", selected.getDiscountedPrice()));
                } else {
                    clearInputs();
                }
            }
        });
    }

    private void getNewOrder() {
        Response response = orh.get(user.getId());
        order = response.readEntity(Order.class);
    }

    private void clearInputs() {
        createNameTextField.setText("");
        unitTextField.setText("");
        quantitySpinner.setValue(0);
        priceTextField.setText("");
        discountSpinner.setValue(0);
        createDiscountedPriceValueLb.setText("");
    }

    private Product createProduct() {
        validProductDetails = true;
        clearFeedbackLabels();

        String name = createNameTextField.getText();
        String unit = unitTextField.getText();
        int quantity = (Integer) quantitySpinner.getValue();

        double price = -1.0;

        try {
            price = Double.parseDouble(priceTextField.getText());
        } catch (NumberFormatException ex) {
            createFeedbackLb.setText("Could not parse price!");
            createFeedbackLb.setForeground(Color.RED);
            validProductDetails = false;
        }

        if (validProductDetails) {
            validate(nameFeedbackLb, "Name", name);
            validate(unitFeedbackLb, "Unit", unit);
            validateQuantity(quantity);
            validatePrice(price);
        }

        if (validProductDetails) {
            Response response = prh.create(name, unit, quantity, price);

            if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
                clearInputs();

                createFeedbackLb.setText("Product successfully created");
                createFeedbackLb.setForeground(Color.GREEN);

                return response.readEntity(Product.class);
            }
        }

        return null;
    }

    private void clearFeedbackLabels() {
        clearLabel(createFeedbackLb);
        clearLabel(nameFeedbackLb);
        clearLabel(unitFeedbackLb);
        clearLabel(quantityFeedbackLb);
        clearLabel(priceFeedbackLb);
    }

    private void validatePrice(double price) {
        if (price < 0) {
            priceFeedbackLb.setText("Price must be > 0");
            priceFeedbackLb.setForeground(Color.RED);
            validProductDetails = false;
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            quantityFeedbackLb.setText("Quantity must be > 0");
            quantityFeedbackLb.setForeground(Color.RED);
            validProductDetails = false;
        }
    }

    private void validate(JLabel feedbackLb, String field, String value) {
        if (nullOrEmpty(value)) {
            feedbackLb.setText(field + " is required");
            feedbackLb.setForeground(Color.RED);
            validProductDetails = false;
        }
    }

    private boolean nullOrEmpty(String value) {
        return value == null || value.equals("");
    }

    private void clearProductDetails() {
        clearLabel(nameLb);
        clearLabel(unitLb);
        clearLabel(priceLb);
        clearLabel(discountLb);
    }

    private void clearLabel(JLabel label) {
        label.setText("");
    }

    private void setProductDetails(Product product) {
        nameLb.setText("Product: " + product.getName());
        unitLb.setText("Quantity: " + product.getQuantity() + " " + product.getUnit());
        priceLb.setText(String.format("Costs: € %.2f", product.getDiscountedPrice()));

        if (product.isDiscounted()) {
            discountLb.setText("Discount: " + product.getDiscountPercentage() + "% off");
        } else {
            discountLb.setText("");
        }
    }

    private void refreshProducts() {
        String sortMethod = getSortMethod();
        Response response = prh.getAll(sortMethod);

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            GenericType<List<Product>> genericTyp = new GenericType<>() {
            };
            List<Product> products = response.readEntity(genericTyp);

            productList.setListData(products.toArray());
            createOrUpdateList.setListData(products.toArray());
        }
    }

    private String getSortMethod() {
        if (priceSortRbtn.isSelected()) {
            return "PRICE";
        } else if (discountSortRbtn.isSelected()) {
            return "DISCOUNT";
        } else {
            return "ID";
        }
    }

    private boolean isNotManager(User user) {
        return !isManager(user);
    }

    private boolean isManager(User user) {
        for (Role role : user.getRoles()) {
            if (role == Role.CATEGORY_MANAGER) {
                return true;
            }
        }

        return false;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        SpinnerModel model = new SpinnerNumberModel(1, 1, 999, 1);
        SpinnerModel discountModel = new SpinnerNumberModel(0, 0, 100, 1);

        quantitySpinner = new JSpinner(model);
        lineItemSpinner = new JSpinner(model);
        discountSpinner = new JSpinner(discountModel);
    }

    public static void main(String[] args) {
        // Here for testing all functionality directly.
        User manager = new User();
        manager.getRoles().add(Role.CATEGORY_MANAGER);

        OrderApplication orderApp = new OrderApplication(manager);
        orderApp.setVisible(true);
    }
}
