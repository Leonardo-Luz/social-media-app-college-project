package ifrs.edu.com.tests.services;

import ifrs.edu.com.models.User;
import ifrs.edu.com.services.UserDAO;

public class UserDAOTest {
	private static UserDAO service = new UserDAO();

	public static boolean run() {
		User result = new User(999, "TEST", "TEST", "TEST");

		if (service.insert(result))
			return false;

		User response = service.get(999);

		if (!response.equals(result)) {
			service.delete(999);
			return false;
		}

		response.setName("Update Name");
		response.setUsername("Update Username");
		response.setPassword("Update Password");

		if (!service.update(response)) {
			service.delete(999);
			return false;
		}

		if (!response.equals(service.get(999))) {
			service.delete(999);
			return false;
		}

		if (service.delete(999))
			return false;

		return true;
	}

}
