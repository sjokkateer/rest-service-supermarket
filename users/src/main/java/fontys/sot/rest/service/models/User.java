package fontys.sot.rest.service.models;

import fontys.sot.rest.service.enums.Role;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static int userId = 0;

    private int id;
    private String name;
    private String password;
    private List<Role> roles;

    public User() {
        super();

        id = ++userId;
        roles = new ArrayList<>();
        // By default anyone will start as customer
        // allowed to enjoy read functionality and order products.
        roles.add(Role.CUSTOMER);
    }

    public User(String name, String password) {
        this();
        this.name = name.toLowerCase();
        this.password = password;
    }

    public User(String name, String password, List<Role> roles) {
        this(name, password);

        this.roles.addAll(roles);
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean verify(String password) {
        return this.password.equals(password);
    }
}
