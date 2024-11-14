package ifrs.edu.com.tests.services;

import ifrs.edu.com.models.Chat;
import ifrs.edu.com.service.ChatDAO;
import ifrs.edu.com.service.UserDAO;

public class ChatDAOTest {
	private static ChatDAO chatService = new ChatDAO();
	private static UserDAO userService = new UserDAO();

	public static boolean run() {
		Chat result = new Chat("Test", userService.get(1));

		Chat response = chatService.get(2);

		if (!chatService.insert(result))
			return false;

		if (!response.equals(result))
			return false;

		response.setTitle("Update Title");

		if (!chatService.update(response))
			return false;

		if (!response.equals(chatService.get(2)))
			return false;

		if (!chatService.delete(2))
			return false;

		return true;
	}

}
