package controller;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Stack;

import twitter4j.Status;

public class Dd {
	
	String user="twitterBackup";
	String password = "twitterBackup";
	Connection conn;
	private static Dd instantzia=new Dd();
	
	private Dd() {
		try{
			Class.forName("org.gjt.mm.mysql.Driver");
//			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		//Open connection
		try{
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TwitterBackup",user,password);
			conn.setAutoCommit(false);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Dd getDd(){
		return instantzia;
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
	
	public void closeConnection(){
		try {
			conn.close();
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
			statement.executeUpdate(query);
			commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insert(String query){
		try {
			Statement statement= conn.createStatement();
			statement.executeUpdate(query);
			commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
