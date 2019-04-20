package controllers;

import java.util.ArrayList;

import database.MessageDAO;
import game.Message;
import game.Player;

public class MainApplication {

	public static void main(String[] args) {
		MessageDAO messagedao = new MessageDAO();
		
		Player player1 = new Player();
		Player player2 = new Player();
		Player player3 = new Player();
		Player player4 = new Player();
		
		player1.setPlayerId(1);
		player2.setPlayerId(2);
		player3.setPlayerId(3);
		player4.setPlayerId(4);
		
		
		
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
		
		
		
		ArrayList<Message> messages = messagedao.getMessages(players);
		
		for(Message message : messages) {
			System.out.println(message.getMessage());
		}
		
		
	}

}
