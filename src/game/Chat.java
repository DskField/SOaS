package game;

import java.util.ArrayList;

public class Chat {
	// variables
	private ArrayList<Message> messages;

	public Chat() {
		messages = new ArrayList<Message>();
	}
	/**
	 * @return returns the Arraylist messages
	 */
	public ArrayList<Message> getChat() {
		return messages;
	}
	
	public void addMessage(String message, Player player) {
		messages.add(new Message(message, player));
	}
}
