package controllers;

import java.util.ArrayList;

import game.Chat;
import game.Message;
import game.Player;

public class MainApplication {

	public static void main(String[] args) {
		ArrayList<Message> messages = new ArrayList<Message>();
		Player player = new Player();
		Chat chat = new Chat();
		
		Message message0 = new Message("hallo", player);
		Message message1 = new Message("dit", player);
		Message message2 = new Message("is", player);
		Message message3 = new Message("een", player);
		Message message4 = new Message("test", player);
		
		Message message5 = new Message("het", player);
		Message message6 = new Message("werkt", player);
		Message message7 = new Message("denk", player);
		Message message8 = new Message("ik", player);
		
		message5.timestamp.setTime(1555765940);
		message6.timestamp.setTime(1);
		
		messages.add(message5);
		messages.add(message6);
		messages.add(message7);
		messages.add(message8);
		
		chat.addMessage(message0);
		chat.addMessage(message1);
		chat.addMessage(message2);
		chat.addMessage(message3);
		chat.addMessage(message4);
		
		if(chat.getChat().get(chat.getChat().size()-1).getTimestamp().before(message6.getTimestamp())) {
			chat.addMessage(message6);
		}
		System.out.println(chat.getChat().get(chat.getChat().size()-1).getTimestamp().before(message6.getTimestamp()));
		for(Message message : chat.getChat()) {
			System.out.println(chat.getChat().get(chat.getChat().size()-1).getTimestamp().before(message6.getTimestamp()));
		}
		

	}

}
