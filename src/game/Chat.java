package game;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Chat {
	/* VARIABLES */
	private ArrayList<Message> messages;

	public Chat() {
		messages = new ArrayList<Message>();
	}

	/**
	 * Add's a message to the chat
	 * 
	 * @param message - {@code Message} that gets added to the {@code Chat}
	 */
	public void addMessage(Message message) {
		messages.add(message);
	}

	/**
	 * Add's multiple messages to the chat
	 * 
	 * @param messages - {@code ArrayList<Message>} that gets added to the {@code Chat}
	 */
	public void addMessages(ArrayList<Message> messages) {
		for (Message message : messages) {
			this.messages.add(message);
		}
	}

	/**
	 * Looks for the last {@code Message} in the {@code Chat}
	 * 
	 * @return returns -The {@code Timestamp} of the last {@code Message} in the {@code Chat}
	 */
	public Timestamp getLastTimestamp() {
		if (!messages.isEmpty()) {
			return messages.get(messages.size() - 1).getTimestamp();
		} else {
			return new Timestamp(0);
		}

	}

	/**
	 * Looks for the last {@code Message} in the {@code Chat}
	 * 
	 * @return returns - The chat time of the last message in the chat
	 */
	public String getLastChatTime() {
		if (!messages.isEmpty()) {
			return messages.get(messages.size() - 1).getChatTime();
		} else {
			return "0";
		}

	}

	/* GETTERS AND SETTERS */
	public ArrayList<Message> getChat() {
		return messages;
	}
}
