package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import game.Die;

class DieDAO extends BaseDAO {
	//This method is only for selects for inserts 'n shit  use super.prepStmnt("REALLY NICE STATEMENT");
    private List<Die> selectDie(String query) {
        List<Die> results = new ArrayList<Die>();
        try (Connection con = super.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet dbResultSet = stmt.executeQuery(query);
            while (dbResultSet.next()) {
                int number = dbResultSet.getInt("number");
                String color = dbResultSet.getString("color");
                Die die = new Die(number, color);
                results.add(die);
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("DieDAO " + e.getMessage());
        }
        return results;
    }
    
    
    List<Die> getAllDies(){
    	return selectDie("Select * from die");
    }
}
