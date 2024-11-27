package ifrs.edu.com.context;

import ifrs.edu.com.models.User;
import ifrs.edu.com.services.UserDAO;

public class AuthProvider {
    private static User user = null;

    public static void login(String username, String password) {
        UserDAO service = new UserDAO();

        User logged = service.login(username, password);

        AuthProvider.user = logged;
    }

    public static void register(String name, String username, String password) {
        UserDAO service = new UserDAO();

        service.insert(new User(name, username, password));

        AuthProvider.user = service.login(username, password);
    }

    public static boolean isLogged() {
        return AuthProvider.user != null;
    }

    public static User getUser() {
        return AuthProvider.user;
    }

    public static void logout() {
        AuthProvider.user = null;
    }

    public static boolean deleteAccount() {
        UserDAO service = new UserDAO();

        Integer id = user.getUserId();

        AuthProvider.logout();

        return service.delete(id);
    }

    // public static boolean isAdmin(){
    // return user.role == "admin"
    // }
}
