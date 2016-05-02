package prueba1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlMethods {
	
	public static void connect(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection c= DriverManager.getConnection("");
		
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}catch(SQLException e){
			
		}
	}

}
