package model;


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
	
	public Dd() {
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
	
	public void instert(String query){
		try {
			Statement statement= conn.createStatement();
			statement.executeUpdate(query);
			commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Stack<String> tweetakIkusi() throws SQLException {
//		ResultSet request = this.select("SELECT id,mesage FROM MyTweets WHERE id BETWEEN "+lehen+" AND "+bigarren+" ORDER BY id DESC");
		ResultSet request = this.select("SELECT id,mesage FROM MyTweets ORDER BY id DESC LIMIT 20");
//		String emaitza = null;
//		request.getAsciiStream(emaitza);
//		return emaitza;
		Stack<String> st=new Stack<String>();
		while(request.next()){
			st.add(request.getString(1));
			}
		return st;
	}
	
	public void tweetaGorde(String userName, Status status){
		System.out.println(userName);
		System.out.println(status.getId()+": "+status.getText()+"    "+status.getCreatedAt());
	}
}
