package ifrs.edu.com.tests.services;

import ifrs.edu.com.models.Chat;
import ifrs.edu.com.services.ChatDAO;
import ifrs.edu.com.services.UserDAO;

public class ChatDAOTest {
	private static ChatDAO chatService = new ChatDAO();
	private static UserDAO userService = new UserDAO();

	public static boolean run() {
		Chat result = new Chat(9999, "Test", userService.get(1));

		if (chatService.insert(result))
			return false;

		Chat response = chatService.get(9999);

		if (!response.equals(result)) {
			chatService.delete(9999);
			return false;
		}

		response.setTitle("Update Title");

		if (!chatService.update(response)) {
			chatService.delete(9999);
			return false;
		}

		if (!response.equals(chatService.get(9999))) {
			chatService.delete(9999);
			return false;
		}

		if (chatService.delete(9999)) {
			chatService.delete(9999);
			return false;
		}
		return true;
	}

}
