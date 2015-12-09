package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import twitter4j.Status;

public class TwitterController {

	private static TwitterController instantzia=new TwitterController();
	private Long azkenTweetId;
	private Long azkenFavId;

	private TwitterController(){	
		azkenFavId=new Long(0);
		azkenTweetId=new Long(0);
	}

	public static TwitterController getTwitterController(){
		return instantzia;
	}

	public Long getAzkenTweetId(String taula){
		//datubasean sartutako azken id-a itzultzen du gehiago egotekotan deskarga bertatik jarraitzeko
		try {			
			Object[] params = new Object[0];
			ResultSet request = DB.getDb().select("SELECT id FROM MyTweets ORDER BY id DESC LIMIT 1",params);	
			request.next();
			return new Long(request.getLong(1));

		} catch (Exception e) {
			System.out.println("Error:  "+e);
		}
		return null;
	}
	
//	public ArrayList<String> tweetakIkusi(String tUser) throws SQLException {
//		//pantailaratutako azken id-tik abiaratuta beste 20 hartzen ditu
//		Object[] params = new Object[2];
//		params[0]=Long.toString(azkenTweetId);
//		params[1]=tUser;
//		ResultSet request = null;
//		if (azkenTweetId.equals(new Long(0))) {
//			request = DB.getDb().select("SELECT id,mesage FROM MyTweets WHERE twitterUser=? ORDER BY id DESC LIMIT 20", params);
//		}
//		else {
//			request = DB.getDb().select("SELECT id,mesage FROM MyTweets WHERE id < ? AND twitterUser=? ORDER BY id DESC LIMIT 20",params); 
//		}
//		ArrayList<String> st=new ArrayList<String>();
//		while(request.next()){
//			st.add(request.getString(2));
//			azkenTweetId = request.getLong(1);
//		}
//		return st;
//	}

	public ArrayList<String> lehentweetakIkusi(String tUser) throws SQLException {
		//Honek lehen 20-ak hartzen ditu (datubasetik), hurrengoak hartzeko beste sql sententzia bat erabili behar delako
		//ResultSet request = Dd.getDd().select("SELECT id,mesage FROM MyTweets WHERE twitterUser='"+tUser+"' ORDER BY id DESC LIMIT 20");
		Object[] params = new Object[1];
		params[0]=tUser;

		ResultSet request = DB.getDb().select("SELECT id,mesage FROM MyTweets WHERE twitterUser=? ORDER BY id DESC LIMIT 20", params);
		ArrayList<String> st=new ArrayList<String>();

		while(request.next()){
			st.add(request.getString(2));
			azkenTweetId = request.getLong(1);
		}
		return st;
	}

	public ArrayList<String> tweetakIkusi(String tUser) throws SQLException {
		//pantailaratutako azken id-tik abiaratuta beste 20 hartzen ditu
		Object[] params = new Object[2];
		params[0]=Long.toString(azkenTweetId);
		params[1]=tUser;
		ResultSet request = DB.getDb().select("SELECT id,mesage FROM MyTweets WHERE id < ? AND twitterUser=? ORDER BY id DESC LIMIT 20",params);
		ArrayList<String> st=new ArrayList<String>();
		while(request.next()){
			st.add(request.getString(2));
			azkenTweetId = request.getLong(1);
		}
		return st;
	}

	public ArrayList<String[]> favIkusi(String tUser) throws SQLException {
		//pantailaratutako azken id-tik abiaratuta beste 20 hartzen ditu
		Object[] params = new Object[2];
		params[0]=azkenFavId.toString();
		params[1]=tUser;
		ResultSet request=null;
		if(!azkenFavId.equals(new Long(0))){
			 request = DB.getDb().select("SELECT id,mesage,name FROM Fav WHERE id < ? AND twitterUser=? ORDER BY id DESC LIMIT 20",params);
		}else{
			 request = DB.getDb().select("SELECT id,mesage,name FROM Fav WHERE twitterUser=? ORDER BY id DESC LIMIT 20", params);
		}
		ArrayList<String[]> st=new ArrayList<String[]>();
		
		while(request.next()){
			String[] stlis=new String[2];
			stlis[0]=request.getString(2);
			stlis[1]="@"+request.getString(3);
			azkenFavId = request.getLong(1);
			st.add(stlis);
		}
		return st;
	}

	public ArrayList<String[]> lehenFavkIkusi(String tUser) throws SQLException {
		//Honek lehen 20-ak hartzen ditu (datubasetik), hurrengoak hartzeko beste sql sententzia bat erabili behar delako
		//ResultSet request = Dd.getDd().select("SELECT id,mesage FROM MyTweets WHERE twitterUser='"+tUser+"' ORDER BY id DESC LIMIT 20");
		Object[] params = new Object[1];
		params[0]=tUser;

		ResultSet request = DB.getDb().select("SELECT id,mesage,name FROM Fav WHERE twitterUser=? ORDER BY id DESC LIMIT 20", params);
		ArrayList<String[]> st=new ArrayList<String[]>();
		
		while(request.next()){
			String[] stlis=new String[2];
			stlis[0]=request.getString(2);
			stlis[1]="@"+request.getString(3);
			st.add(stlis);
			azkenFavId = request.getLong(1);
		}
		return st;
	}

	public void tweetaGorde(String userName, Status status){
		Object[] params = new Object[4];

		params[0]=Long.toString(status.getId());
		params[1]=status.getText();
		params[2]=status.getUser().getScreenName();
		params[3]=userName;
		DB.getDb().insert("INSERT INTO MyTweets(id,mesage,name,twitterUser)VALUES(?,?,?,?)", params);
	}

	public void favGorde(String userName, Status status){
		Object[] params = new Object[4];
		params[0]=Long.toString(status.getId());
		params[1]=status.getText();
		params[2]=status.getUser().getScreenName();
		params[3]=userName;
		DB.getDb().insert("INSERT INTO Fav(id,mesage,name,twitterUser)VALUES(?,?,?,?)", params);
	}

	public void followerakGorde(String id, String name, String user){
		Object[] params = new Object[3];
		params[0] = id;
		params[1] = name;
		params[2] = user;
		System.out.println(params[0]+"   "+params[1]+"    "+params[2]);
		DB.getDb().insert("INSERT INTO Followers(id, name, twitterUser)VALUES(?,?,?)", params);
	}

}
