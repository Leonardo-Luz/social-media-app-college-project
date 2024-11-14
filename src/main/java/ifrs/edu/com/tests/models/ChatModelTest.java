package ifrs.edu.com.tests.models;

import ifrs.edu.com.models.Chat;
import ifrs.edu.com.models.User;

public class ChatModelTest {

	public static boolean run() {
		Chat chat = new Chat("Test 1", new User());

		String toString = "\nChat 0" +
				"\nTitle: Test 1" +
				"\nAdmin: null";

		if (!toString.equals(chat.toString()))
			return false;

		chat.setTitle("Test 2");

		if (!chat.getTitle().equals("Test 2"))
			return false;

		return true;
	}
}
