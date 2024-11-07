package ifrs.edu.com.context;

import java.sql.SQLException;

import ifrs.edu.com.models.User;
import ifrs.edu.com.service.UserDAO;

class AuthProvider {
    private User user = null;

    public void login(UserDAO service, String username, String password) {
        try {
            User logged = service.login(username, password);

            this.user = logged;
        } catch (SQLException err) {
            System.out.println(err);
        }
    }

    public void register(UserDAO service, String name, String username, String password) {
        try {
            if (service.insert(new User(name, username, password))) {
                this.user = service.login(username, password);
            }
        } catch (SQLException err) {
            System.out.println(err);
        }
    }

    public boolean isLogged() {
        return user != null;
    }

    public User getUser() {
        return user;
    }
}
