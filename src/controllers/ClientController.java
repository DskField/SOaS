package controllers;

import java.util.ArrayList;

import client.Client;
import client.Lobby;
import client.User;
import game.Player;

public class ClientController {

	private Client client;
	private User user;
	private ArrayList<Lobby> clientLobbies; // Remote to client's Lobby list

	public ClientController() {

		client = new Client();
		user = new User(0, null); // Stub values for the constructor
		clientLobbies = client.getLobbies();
	}

	public void acceptChallenge() {
		// TODO Set playerstatus to 'geaccepteerd'
	}

	public void denyChallenge() {
		// TODO Set playerstatus to 'geweigerd'
		// TODO Wait on game.Player
	}

	public void joinMatch() {
		
	}

	public void invite(Player player) {

		int dbGameID = 0; // Set a stub value to stop the compiler from complaining

		// Creates new lobby if the user is not inside of an inviting lobby
		if (client.isUserInPreLobby() == false) {
			clientLobbies.add(new Lobby(dbGameID));
		}

		// Grabs the lobby that was just created, using dbGameID as the index
		clientLobbies.get(dbGameID).addPlayerToLobby(player);
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

		// TODO Set player status to 'afgebroken'
		// However, game.Player needs to change for this
	}

	public void updateClient() {

		// TODO fetch query to update the current lobbies
	}

	public String findUser(User player) {

		String username = player.getLoginName();

		return username;
	}
}
