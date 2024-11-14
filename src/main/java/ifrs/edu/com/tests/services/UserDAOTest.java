package ifrs.edu.com.tests.services;

import ifrs.edu.com.models.User;
import ifrs.edu.com.service.UserDAO;

public class UserDAOTest {
	private static UserDAO service = new UserDAO();

	public static boolean run() {
		User result = new User("Jose", "roberto", "carlos");

		User response = service.get(3);

		if (!service.insert(result))
			return false;

		if (!response.equals(result))
			return false;

		response.setName("Update Name");
		response.setUsername("Update Username");
		response.setPassword("Update Password");

		if (!service.update(response))
			return false;

		if (!response.equals(service.get(3)))
			return false;

		if (!service.delete(3))
			return false;

		return true;
	}

}
