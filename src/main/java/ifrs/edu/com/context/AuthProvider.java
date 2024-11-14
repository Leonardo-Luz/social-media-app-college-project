package ifrs.edu.com.context;

import ifrs.edu.com.models.User;
import ifrs.edu.com.service.UserDAO;

public class AuthProvider {
    private User user = null;

    public void login(UserDAO service, String username, String password) {
        User logged = service.login(username, password);

        this.user = logged;
    }

    public void register(UserDAO service, String name, String username, String password) {
        if (service.insert(new User(name, username, password)))
            this.user = service.login(username, password);

    }

    public boolean isLogged() {
        return user != null;
    }

    public User getUser() {
        return user;
    }
}
