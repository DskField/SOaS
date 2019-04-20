package game;

import java.util.ArrayList;

public class Chat {
	// variables
	private ArrayList<Message> messages;

	public Chat() {
		messages = new ArrayList<Message>();
	}
	
	public ArrayList<Message> getChat() {
		return messages;
	}
	
	public void addMessage(Message message) {
		messages.add(message);
	}
	
// test methods for checking the timestamp of the last message in the chat
//against the message that will be added
//	private void addMessage(Message message) {
//		if(messages.get(messages.size() -1).getTimestamp().after(message.getTimestamp())) {
//			messages.add(message);
//		}
//	}
}
