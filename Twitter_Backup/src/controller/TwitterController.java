package controller;

import java.awt.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Stack;

import twitter4j.Status;

public class TwitterController {

	private static TwitterController instantzia=new TwitterController();
	private long azkenTweetId;
	
	private TwitterController(){	}
	
	public static TwitterController getTwitterController(){
		return instantzia;
	}
	
	public Long getAzkenTweetId(String taula){
		//datubasean sartutako azken id-a itzultzen du gehiago egotekotan deskarga bertatik jarraitzeko
		try {			
			//ResultSet request = Dd.getDd().select("SELECT id FROM MyTweets ORDER BY id DESC LIMIT 1");			
			Object[] params = new Object[0];
			ResultSet request = Dd.getDd().select("SELECT id FROM MyTweets ORDER BY id DESC LIMIT 1",params);	
			request.next();
			return new Long(request.getLong(1));
			
		} catch (Exception e) {
			System.out.println("Error:  "+e);
		}
		return null;
	}
	
	public LinkedList<String> lehentweetakIkusi(String tUser) throws SQLException {
		//Honek lehen 20-ak hartzen ditu (datubasetik), hurrengoak hartzeko beste sql sententzia bat erabili behar delako
		//ResultSet request = Dd.getDd().select("SELECT id,mesage FROM MyTweets WHERE twitterUser='"+tUser+"' ORDER BY id DESC LIMIT 20");
		Object[] params = new Object[1];
		params[0]=tUser;
		ResultSet request = Dd.getDd().select("SELECT id,mesage FROM MyTweets WHERE twitterUser=? ORDER BY id DESC LIMIT 20", params);
		LinkedList<String> st=new LinkedList<String>();
		while(request.next()){
			st.add(request.getString(2));
			azkenTweetId = request.getLong(1);
			}
		return st;
	}
	
	public LinkedList<String> tweetakIkusi(String tUser) throws SQLException {
		//pantailaratutako azken id-tik abiaratuta beste 20 hartzen ditu
		//
		
		//ResultSet request = Dd.getDd().select("SELECT id,mesage FROM MyTweets WHERE id < '"+azkenTweetId+"' AND twitterUser='"+tUser+"' ORDER BY id DESC LIMIT 20");
		Object[] params = new Object[2];
		params[0]=Long.toString(azkenTweetId);
		params[1]=tUser;
		ResultSet request = Dd.getDd().select("SELECT id,mesage FROM MyTweets WHERE id < ? AND twitterUser=? ORDER BY id DESC LIMIT 20",params);
		LinkedList<String> st=new LinkedList<String>();
		while(request.next()){
			st.add(request.getString(2));
			azkenTweetId = request.getLong(1);
			}
		return st;
	}
	
	public void tweetaGorde(String userName, Status status){
		//System.out.println(userName);
		//System.out.println(status.getId()+": "+status.getText()+"    "+status.getCreatedAt());
		//System.out.println("INSERT INTO TwitterBackup.MyTweets(`id`,`mesage`,`name`,`twitterUser`)VALUES("+status.getId()+",'"+status.getText() +"','"+status.getUser().getScreenName()+"','"+userName+"')");
		//Dd.getDd().insert("INSERT INTO MyTweets(id,mesage,name,twitterUser)VALUES('"+status.getId()+"','"+status.getText() +"','"+status.getUser().getScreenName()+"','"+userName+"')");		
		Object[] params = new Object[4];
		params[0]=Long.toString(status.getId());
		params[1]=status.getText();
		params[2]=status.getUser().getScreenName();
		params[3]=userName;
		//Dd.getDd().insert("INSERT INTO MyTweets(id,mesage,name,twitterUser)VALUES('?','?','?','?')", params);
		Dd.getDd().insert("INSERT INTO MyTweets(id,mesage,name,twitterUser)VALUES(?,?,?,?)", params);
	}
	
	public void favGorde(String userName, Status status){
		//Dd.getDd().insert("INSERT INTO Fav(id,mesage,name,twitterUser)VALUES('"+status.getId()+"','"+status.getText()+"','"+status.getUser().getScreenName()+"','"+userName+"')");
		Object[] params = new Object[4];
		params[0]=Long.toString(status.getId());
		params[1]=status.getText();
		params[2]=status.getUser().getScreenName();
		params[3]=userName;
		Dd.getDd().insert("INSERT INTO Fav(id,mesage,name,twitterUser)VALUES(?,?,?,?)", params);
	}
	
	public void followerakGorde(String id, String name, String user){
		Object[] params = new Object[3];
		params[0] = id;
		params[1] = name;
		params[2] = user;
		System.out.println(params[0]+"   "+params[1]+"    "+params[2]);
		Dd.getDd().insert("INSERT INTO Followers(id, name, twitterUser)VALUES(?,?,?)", params);
	}
	
}
