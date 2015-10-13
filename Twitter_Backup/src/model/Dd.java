package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Dd {
	
	String user="twitterBackup";
	String password = "twitterBackup";
	Connection conn;
	
	public Dd() {
		try{
			Class.forName("org.gjt.mm.mysql.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		//Open connection
		try{
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306",user,password);
			conn.setAutoCommit(false);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void commit() {
		// TODO Auto-generated method stub
		////////to-do
		try{
			conn.commit();
		}catch(Exception e){
			
		}
	}
	
	public void rollback(){
		try {
			conn.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet select(String query){
		
		ResultSet request = null;
		Statement statement;
		try {
			statement = conn.createStatement();
			request = statement.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return request;
		/*while (erantzuna.next()){
			i=Integer.parseInt(erantzuna.getString(1));
			System.out.println(i);
		}*/
	}
	
	public void update(String query){
		try {
			Statement statement= conn.createStatement();
			//int erantzuna;
			//erantzuna = statement.executeUpdate(query);
			statement.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String tweetakIkusi() throws SQLException {
		ResultSet request = this.select("");
		String emaitza = null;
		request.getAsciiStream(emaitza);
		return emaitza;
	}
	
}