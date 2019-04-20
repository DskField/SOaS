package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import game.Message;
import game.Player;

public class MessageDAO extends BaseDAO {

	private ArrayList<Message> selectMessage(String query, ArrayList<Player> players) {
		ArrayList<Message> results = new ArrayList<Message>();
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			while (dbResultSet.next()) {
				String text = dbResultSet.getString("message");
				int playerId = dbResultSet.getInt("player_idplayer");
				Timestamp timestamp = dbResultSet.getTimestamp("time");
				for (Player player : players) {
					if (player.getPlayerId() == playerId) {
						Message message = new Message(text, player, timestamp);
						results.add(message);
					}
				}
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("MessageDAO " + e.getMessage());
		}
		return results;

	}
	
	public ArrayList<Message> getMessages(ArrayList<Player> players){
		return selectMessage("select * from chatline", players);
	}

}