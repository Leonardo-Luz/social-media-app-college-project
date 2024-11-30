package ifrs.edu.com.tests.services;

import ifrs.edu.com.models.Chat;
import ifrs.edu.com.models.Message;
import ifrs.edu.com.models.User;
import ifrs.edu.com.services.ChatDAO;
import ifrs.edu.com.services.MessageDAO;
import ifrs.edu.com.services.UserDAO;

public class MessageDAOTest {
	private static MessageDAO messageService = new MessageDAO();
	private static UserDAO userService = new UserDAO();
	private static ChatDAO chatService = new ChatDAO();

	private static boolean abort() {

		chatService.delete(8888);
		userService.delete(8888);

		messageService.delete(9999);

		return false;
	}

	public static boolean run() {
		userService.insert(new User(8888, "teste", "fwe324q3", "teste"));
		chatService.insert(new Chat(8888, "wfewwdsa", userService.get(8888)));

		Message result = new Message(9999, "Test", userService.get(8888), chatService.get(8888));

		if (messageService.insert(result))
			return abort();

		Message response = messageService.get(9999);

		if (!response.equals(result))
			return abort();

		response.setText("Update Text");

		if (!messageService.update(response))
			return abort();

		if (!response.equals(messageService.get(9999)))
			return abort();

		if (messageService.delete(9999))
			return abort();

		chatService.delete(8888);
		userService.delete(8888);

		return true;
	}

}
