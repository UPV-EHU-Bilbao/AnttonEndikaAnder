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
	
	public Long getAzkenId(){
		try {
			ResultSet request = this.select("SELECT id FROM MyTweets ORDER BY id DESC LIMIT 1");			
			request.next();
			return new Long(request.getLong(1));
			
		} catch (Exception e) {
			System.out.println("Error:  "+e);
		}
		return new Long(-1);		//taula hutsik dagoenean
	}
	
	public Stack<String> tweetakIkusi(Long lehen, Long bigarren) throws SQLException {
		ResultSet request = this.select("SELECT id,mesage FROM MyTweets WHERE id BETWEEN "+getAzkenId()+" AND "+(getAzkenId()+2000)+" ORDER BY id DESC");
//		ResultSet request = this.select("SELECT id,mesage FROM MyTweets ORDER BY id DESC LIMIT 20");
//		String emaitza = null;
//		request.getAsciiStream(emaitza);
//		return emaitza;
		Stack<String> st=new Stack<String>();
		while(request.next()){
			st.add(request.getString(2));
			}
		return st;
	}
	
	public void tweetaGorde(String userName, Status status){
		//System.out.println(userName);
		//System.out.println(status.getId()+": "+status.getText()+"    "+status.getCreatedAt());
		//System.out.println("INSERT INTO TwitterBackup.MyTweets(`id`,`mesage`,`name`,`twitterUser`)VALUES("+status.getId()+",'"+status.getText() +"','"+status.getUser().getScreenName()+"','"+userName+"')");
		insert("INSERT INTO TwitterBackup.MyTweets(id,mesage,name,twitterUser)VALUES('"+status.getId()+"','"+status.getText() +"','"+status.getUser().getScreenName()+"','"+userName+"')");		
	}
	
	public String[] getTwitterSession(){
		String[] session = null;
		try {
			ResultSet request = select("SELECT twitterUser,tokenSecret,token FROM UserTwitter");
			if (request.next()==true){
				session = new String[3];
				session[0] = request.getString(1);
				session[1] = request.getString(2);
				session[2] = request.getString(3);
			}
		} catch (SQLException e) {
			System.out.println("Error:  "+e);
		}
		
		return session;
	}
	
	public void newTwitterSession(String user, String tokenSecret, String token){
		insert("INSERT INTO UserTwitter(twitterUser,tokenSecret,token)VALUES('" + user + "','" + tokenSecret + "','" + token + "')");
	}
	public boolean login(String text, String text2) {
		// TODO Auto-generated method stub
		return true;
	}
}
