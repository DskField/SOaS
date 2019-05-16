package database;

import java.sql.Connection;

public class LoginDAO {
	Connection con;
	
	public LoginDAO(Connection con) {
		this.con = con;
	}
	
}
