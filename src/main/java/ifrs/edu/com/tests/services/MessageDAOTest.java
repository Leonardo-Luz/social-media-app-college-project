package ifrs.edu.com.tests.services;

import ifrs.edu.com.models.Message;
import ifrs.edu.com.service.ChatDAO;
import ifrs.edu.com.service.MessageDAO;
import ifrs.edu.com.service.UserDAO;

//  TODO: Change to abstract generic class
public class MessageDAOTest {
	private static MessageDAO messageService = new MessageDAO();
	private static UserDAO userService = new UserDAO();
	private static ChatDAO chatService = new ChatDAO();

	public static boolean run() {
		Message result = new Message(9999, "Test", userService.get(1), chatService.get(2));

		if (messageService.insert(result))
			return false;

		Message response = messageService.get(9999);

		if (!response.equals(result)) {
			messageService.delete(9999);
			return false;
		}

		response.setText("Update Text");

		if (!messageService.update(response)) {
			messageService.delete(9999);
			return false;
		}

		if (!response.equals(messageService.get(9999))) {
			messageService.delete(9999);
			return false;
		}

		if (messageService.delete(9999)) {
			messageService.delete(9999);
			return false;
		}

		return true;
	}

}
