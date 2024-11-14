package ifrs.edu.com.tests.models;

import ifrs.edu.com.models.Chat;
import ifrs.edu.com.models.Message;
import ifrs.edu.com.models.User;

// TODO: Add createdAt and UpdatedAt tests

public class MessageModelTest {

	public static boolean run() {
		Message message = new Message("Test 1", new User(1, "user", "user", "user"),
				new Chat(1, "chat", new User(1, "user", "user", "user")));
		Message wrong = new Message("WRONG", new User(1, "user", "user", "user"),
				new Chat(1, "chat", new User(1, "user", "user", "user")));
		Message right = new Message("Test 1", new User(1, "user", "user", "user"),
				new Chat(1, "chat", new User(1, "user", "user", "user")));

		String toString = "\nMessage 0" +
				"\nSender: user" +
				"\nChat: chat" +
				"\nText: Test 1";

		if (!toString.equals(message.toString()))
			return false;

		if (!message.equals(right))
			return false;

		if (message.equals(wrong))
			return false;

		message.setText("Test 2");

		if (!message.getText().equals("Test 2"))
			return false;

		return true;
	}
}
