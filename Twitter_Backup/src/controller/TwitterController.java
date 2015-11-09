package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
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
			ResultSet request = Dd.getDd().select("SELECT id FROM MyTweets ORDER BY id DESC LIMIT 1");			
			request.next();
			return new Long(request.getLong(1));
			
		} catch (Exception e) {
			System.out.println("Error:  "+e);
		}
		return null;
	}
	
	public Stack<String> lehentweetakIkusi() throws SQLException {
		//Honek lehen 20-ak hartzen ditu (datubasetik), hurrengoak hartzeko beste sql sententzia bat erabili behar delako
		ResultSet request = Dd.getDd().select("SELECT id,mesage FROM MyTweets ORDER BY id DESC LIMIT 20");
		Stack<String> st=new Stack<String>();
		while(request.next()){
			st.add(request.getString(2));
			azkenTweetId = request.getLong(1);
			}
		return st;
	}
	
	public Stack<String> tweetakIkusi() throws SQLException {
		//pantailaratutako azken id-tik abiaratuta beste 20 hartzen ditu
		ResultSet request = Dd.getDd().select("SELECT id,mesage FROM MyTweets WHERE id < "+azkenTweetId+" ORDER BY id DESC LIMIT 20");
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
		Dd.getDd().insert("INSERT INTO TwitterBackup.MyTweets(id,mesage,name,twitterUser)VALUES('"+status.getId()+"','"+status.getText() +"','"+status.getUser().getScreenName()+"','"+userName+"')");		
	}
	
	public void favGorde(String userName, Status status){
		Dd.getDd().insert("INSERT INTO TwitterBackup.Fav(id,mesage,name,twitterUser)VALUES('"+status.getId()+"','"+status.getText()+"','"+status.getUser().getScreenName()+"','"+userName+"')");
	}
	
	
}
