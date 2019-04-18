package client;

import java.util.ArrayList;

// Importeer Player uit de Game package
import game.Player;

public class Client {
	
	private ArrayList<Lobby> lobbies;
	private User user;

	public void acceptChallenge() {

	}

	public void denyChallenge() {

	}

	public void inviteUser(Player player) {

		// Grijp hoogste GameID van de database, voeg 1 toe
		// GameID is hier dus dummy data
		// In het eindproduct moeten we hier een getal aan zien te hangen door een connectie te maken met een database
		
		// ... We gaan ervanuit dat hij hier de waarde uit de database heeft gehaald ...
		int dbGameID = 0; // Stel waarde in zodat de compiler niet zeurt
		
		// Maakt nieuwe lobby aan
		
		lobbies.add(new Lobby(dbGameID));
		
		
	}

	public void updateClient() {

	}

	public String findUser(User player) {

		String username = player.getInlognaam();

		return username;
	}
}
