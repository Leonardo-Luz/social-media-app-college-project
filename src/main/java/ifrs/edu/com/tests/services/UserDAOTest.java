package ifrs.edu.com.tests.services;

import ifrs.edu.com.models.User;
import ifrs.edu.com.services.UserDAO;

public class UserDAOTest {
	private static UserDAO service = new UserDAO();

	public static boolean abort() {
		service.delete(9999);

		return false;
	}

	public static boolean run() {
		User result = new User(9999, "TEST", "TEST", "TEST");

		if (service.insert(result))
			return abort();

		User response = service.get(9999);

		if (!response.equals(result))
			return abort();

		response.setName("Update Name");
		response.setUsername("Update Username");
		response.setPassword("Update Password");

		if (!service.update(response))
			return abort();

		if (!response.equals(service.get(9999)))
			return abort();

		if (service.delete(9999))
			return abort();

		return true;
	}

}
