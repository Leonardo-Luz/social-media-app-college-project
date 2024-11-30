package ifrs.edu.com.tests.services;

import ifrs.edu.com.models.Chat;
import ifrs.edu.com.models.User;
import ifrs.edu.com.services.ChatDAO;
import ifrs.edu.com.services.UserDAO;

public class ChatDAOTest {
	private static ChatDAO chatService = new ChatDAO();
	private static UserDAO userService = new UserDAO();

	public static boolean abort() {
		chatService.delete(9999);
		userService.delete(8888);

		return false;
	}

	public static boolean run() {
		userService.insert(new User(8888, "teste", "ew1qwess", "teste"));

		Chat result = new Chat(9999, "Test", userService.get(8888));

		if (chatService.insert(result))
			return abort();

		Chat response = chatService.get(9999);

		if (!response.equals(result))
			return abort();

		response.setTitle("Update Title");

		if (!chatService.update(response))
			return abort();

		if (!response.equals(chatService.get(9999)))
			return abort();

		if (chatService.delete(9999))
			return abort();

		userService.delete(8888);

		return true;
	}

}
