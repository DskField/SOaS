package client;

import java.util.ArrayList;

public class Client {

	private ArrayList<Lobby> lobbies;
	private boolean userInPreLobby; // This exists to check if the client user is already inviting people to a lobby

	public ArrayList<Lobby> getLobbies() {
		return lobbies;
	}

	public boolean isUserInPreLobby() {
		return userInPreLobby;
	}

	public void setUserInPreLobby(boolean userIsInPreLobby) {
		this.userInPreLobby = userIsInPreLobby;
	}
}
