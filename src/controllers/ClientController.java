package controllers;

import client.Client;

public class ClientController {

	private boolean checkUpdateClient = false;
	
	private Client client;
	private GameController gamecontroller;
	private MainApplication mainapplication;

	public ClientController(MainApplication mainapplication, String username) {
		this.mainapplication = mainapplication;
		this.gamecontroller = new GameController(mainapplication);
		client = new Client(username);
	}

	public void updateClient() {		
		// 3 to 6 seconds
		while (checkUpdateClient) {
			client.updateClient();
		}
	}
}
