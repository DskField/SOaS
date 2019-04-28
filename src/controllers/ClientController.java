package controllers;

import client.Client;
import client.Lobby;
import client.User;
import game.Player;

public class ClientController {

	private Client client;

	public ClientController() {

		client = new Client();
	}

	public void acceptChallenge() {

	}

	public void denyChallenge() {

	}

	public void joinMatch() {

	}

	public void invite(Player player) {

		int dbGameID = 0; // Set a stub value to stop the compiler from complaining

		// Creates new lobby if the user is not inside of an inviting lobby
		if (client.isUserInPreLobby() == false) {
			client.getLobbies().add(new Lobby(dbGameID));
		}

		// Grabs the lobby that was just created, using dbGameID as the index
		client.getLobbies().get(dbGameID).addPlayerToLobby(player);
	}

	public void userLogin() {

		// Check in database if user-provided info corresponds
		// with info inside the database
	}

	public void userRegister() {

		// Write user-provided info to the database

		// Scan line
	}

	public void exit() {

	}

	public void logout() {

		// Set user status to "offline" or smth in database
	}

	public void updateClient() {

	}

	public String findUser(User player) {

		String username = player.getInlognaam();

		return username;
	}
}
