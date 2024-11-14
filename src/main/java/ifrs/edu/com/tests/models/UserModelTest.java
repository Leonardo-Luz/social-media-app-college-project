package ifrs.edu.com.tests.models;

import ifrs.edu.com.models.User;

public class UserModelTest {

	public static boolean run() {
		User user = new User("Robert", "Carlos", "1234");

		String toString = "\nUser 0" +
				"\nName: Roberto" +
				"\nUsername: Carlos" +
				"\nPassword: 1234";

		if (!toString.equals(user.toString()))
			return false;

		user.setName("Almeida");
		user.setUsername("Jose"); // has to be unique
		user.setPassword("4321");

		if (!user.getName().equals("Almeida") ||
				!user.getUsername().equals("Jose") ||
				!user.getPassword().equals("4321"))
			return false;

		return true;
	}
}
