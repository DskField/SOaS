package client;

import java.util.ArrayList;

// Importeer Player uit de Game package
import game.Player;

public class Client {

	private ArrayList<Lobby> lobbies;
	private Player player;
	private boolean userInPreLobby; // This exists to check if the client user is already inviting people to a lobby

	public ArrayList<Lobby> getLobbies() {
		return lobbies;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean isUserInPreLobby() {
		return userInPreLobby;
	}

	public void setUserInPreLobby(boolean userIsInPreLobby) {
		this.userInPreLobby = userIsInPreLobby;
	}
}
