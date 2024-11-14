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
		Message result = new Message("Test", userService.get(1), chatService.get(1));

		Message response = messageService.get(2);

		if (!messageService.insert(result))
			return false;

		if (!response.equals(result))
			return false;

		response.setText("Update Text");

		if (!messageService.update(response))
			return false;

		if (!response.equals(messageService.get(2)))
			return false;

		if (!messageService.delete(2))
			return false;

		return true;
	}

}
