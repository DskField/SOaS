package client;

import java.util.ArrayList;

public class Client {

	private ArrayList<Lobby> lobbies;
	private boolean userInPreLobby; // This exists to check if the client user is already inviting people to a lobby

	private String username;
	private String password;

	public Client() {
		lobbies = new ArrayList<Lobby>();
	}

	public void acceptChallenge() {
		// TODO Set user status to 'geaccepteerd'
	}

	public void denyChallenge() {
		// TODO Set user status to 'geweigerd'

	}

	public void joinMatch() {

	}

	public void invite(User invUser) {

		int dbGameID = 0; // Set a stub value to stop the compiler from complaining

		// Creates new lobby if the user is not inside of an inviting lobby
		if (this.isUserInPreLobby() == false) {
			lobbies.add(new Lobby(dbGameID));
		}

		// Grabs the lobby that was just created, using dbGameID as the index
		lobbies.get(dbGameID).addUserToLobby(invUser);
	}

	public ArrayList<Lobby> getLobbies() {
		return lobbies;
	}

	public boolean isUserInPreLobby() {
		return userInPreLobby;
	}

	public void setUserInPreLobby(boolean userIsInPreLobby) {
		this.userInPreLobby = userIsInPreLobby;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
