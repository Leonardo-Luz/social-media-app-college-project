package ifrs.edu.com.context;

import ifrs.edu.com.models.User;
import ifrs.edu.com.service.UserDAO;

public class AuthProvider {
    private static User user = null;

    public static boolean login(UserDAO service, String username, String password) {
        User logged = service.login(username, password);

        AuthProvider.user = logged;

        return user != null;
    }

    public static boolean register(UserDAO service, String name, String username, String password) {
        if (!service.insert(new User(name, username, password))) {
            AuthProvider.user = service.login(username, password);
            return true;
        }

        return false;
    }

    public static boolean isLogged() {
        return AuthProvider.user != null;
    }

    public static User getUser() {
        return AuthProvider.user;
    }
}
