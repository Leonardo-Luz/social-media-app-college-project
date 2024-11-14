package ifrs.edu.com.tests.models;

import ifrs.edu.com.models.Chat;
import ifrs.edu.com.models.Message;
import ifrs.edu.com.models.User;

// TODO: Add createdAt and UpdatedAt tests

public class MessageModelTest {

	public static boolean run() {
		Message message = new Message("Test 1", new User(), new Chat());

		String toString = "\nMessage 0" +
				"\nSender: null" +
				"\nChat: null" +
				"\nText: Test 1";

		if (!toString.equals(message.toString()))
			return false;

		message.setText("Test 2");

		if (!message.getText().equals("Test 2"))
			return false;

		return true;
	}
}
