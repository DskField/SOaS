package controllers;

import client.Client;
import database.PersistenceFacade;

public class ClientController {

	private boolean checkUpdateClient = false;
	
	private Client client;
	private GameController gamecontroller;
	private MainApplication mainapplication;
	private PersistenceFacade persistencefacade;

	public ClientController(MainApplication mainapplication, String username) {
		this.persistencefacade = new PersistenceFacade();
		this.mainapplication = mainapplication;
		this.gamecontroller = new GameController(mainapplication);
		client = new Client(username, persistencefacade);
	}

	public boolean handleLogin(String username, String password) {
		
		
		return false;
	}
	
	public void updateClient() {		
		// 3 to 6 seconds
		while (checkUpdateClient) {
			client.updateClient();
		}
	}
}
