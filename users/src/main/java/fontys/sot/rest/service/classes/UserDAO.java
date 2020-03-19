package fontys.sot.rest.service.classes;

import fontys.sot.rest.service.enums.Role;
import fontys.sot.rest.service.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private List<User> users = new ArrayList<>();

    public UserDAO() {
        // Perhaps make these separate classes, but we'll see.
        List<Role> cmRoles = new ArrayList<>() {{ add(Role.CATEGORY_MANAGER); }};
        User cm = new User("manager", "password1234", cmRoles);

        add(cm);
        add(new User("customer", "password"));
    }

    public boolean add(User user) {
        if (alreadyExists(user)) {
            return false;
        }

        users.add(user);
        return true;
    }

    private boolean alreadyExists(User user) {
        for (User u: users) {
            String newUserName = user.getName();
            String existingUserName = u.getName();

            if (newUserName.equals(existingUserName)) {
                return true;
            }
        }

        return false;
    }

    public List<User> getUsers() {
        return users;
    }

    public User find(int id) {
        for (User user: getUsers()) {
            if (user.getId() == id) {
                return user;
            }
        }

        return null;
    }

    private User find(String name) {
        for (User user: users) {
            if (user.getName().toLowerCase().equals(name.toLowerCase())) {
                return user;
            }
        }

        return null;
    }

    public User authenticate(String name, String password) {
        User user = find(name);

        if (user != null && user.verify(password)) {
            return user;
        }

        return null;
    }
}
