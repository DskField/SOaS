package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import client.Client;
import client.Lobby;

public class ClientDAO extends BaseDAO {
	Connection con = super.getConnection();
	
	private Client selectLobbies(String query) {
		ArrayList<Lobby> results = new ArrayList<Lobby>();
		// public Client(ArrayList<Lobby> lobbies, User user, ArrayList<Challenge> challenges) {
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();
			
			while (dbResultSet.next()) {
				// Separated the variable on purpose for clarity
				
				
				//Lobby lobby = new Lobby();
				//results.add(lobby);
			}
			
		} catch (SQLException e) {
			System.err.println("ClientDAO: " + e.getMessage());
		}
		return null;
	}
	
	private ArrayList<Integer> selectGames(String username) {
		ArrayList<Integer> results = new ArrayList<Integer>();
		return null;
	}
	
	Client getClient(String username) {
		return null;
	}
}
