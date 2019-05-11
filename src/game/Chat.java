package game;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Chat {
	// variables
	private ArrayList<Message> messages;

	public Chat() {
		messages = new ArrayList<Message>();
	}

	/**
	 * add's a message to the chat
	 * 
	 * @param message - message that gets added to the chat
	 */
	public void addMessage(Message message) {
		messages.add(message);
	}

	/**
	 * add's multiple messages to the chat
	 * 
	 * @param messages ArrayList of messages that get added to the chat
	 */
	public void addMessages(ArrayList<Message> messages) {
		for (Message message : messages) {
			this.messages.add(message);
		}
	}

	/**
	 * looks for the last Message in the chat object and returns the timestamp
	 * 
	 * @return returns the Timestamp of the last message in the chat
	 */
	public Timestamp getLastTimestamp() {
		if (!messages.isEmpty()) {
			return messages.get(messages.size() - 1).getTimestamp();
		} else {
			return new Timestamp(0);
		}

	}
	
	/**
	 * looks for the last Message in the chat object and returns the chat time
	 * 
	 * @return returns the chat time of the last message in the chat
	 */
	public String getLastChatTime() {
		if (!messages.isEmpty()) {
			return messages.get(messages.size() - 1).getChatTime();
		} else {
			return "0";
		}

	}

	//getters & setters
	public ArrayList<Message> getChat() {
		return messages;
	}
}
