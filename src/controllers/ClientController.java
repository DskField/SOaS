package controllers;

import java.util.ArrayList;

import client.Client;
import client.Lobby;

public class ClientController {

	private Client client;
	private ArrayList<Lobby> clientLobbies; // Remote to client's Lobby list

	public ClientController() {

		client = new Client();
		clientLobbies = client.getLobbies();
	}

//	public void exit() {
//	System.exit(0)
//	}

	public void logout() {
		
		// TODO waiting for view so a pane can be set back
	}

	public void updateClient() {

		// TODO fetch query to update the current lobbies
	}

//	public String findUser(User user) {
//
//		String username = user.getLoginName();
//
//		return username;
//	}
}
