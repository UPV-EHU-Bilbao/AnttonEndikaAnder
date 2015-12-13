package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ExportController {

	private static ExportController instantzia=new ExportController();

	private ExportController(){	

	}

	public static ExportController getExportController(){
		return instantzia;
	}
	
	public ArrayList<ArrayList<String>> getTweets(String user){
		//datubasean sartutako id-a zaharrena edo berriena itzultzen du deskargak kudeatzeko
		Object[] params = new Object[1];
		params [0] = user;			
		ResultSet request = DB.getDb().select("SELECT id,mesage FROM MyTweets WHERE twitterUser=?",params);	
		ArrayList<ArrayList<String>> tweets = new ArrayList<ArrayList<String>>();
		try {
			while(request.next()){
				ArrayList<String> tweet = new ArrayList<String>();
				tweet.add(request.getString(1));
				tweet.add(request.getString(2));
				tweets.add(tweet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tweets;
	}
	
	public ArrayList<ArrayList<String>> getFavs(String user){
		//datubasean sartutako id-a zaharrena edo berriena itzultzen du deskargak kudeatzeko
		Object[] params = new Object[1];
		params [0] = user;			
		ResultSet request = DB.getDb().select("SELECT id,name,mesage FROM Fav WHERE twitterUser=?",params);	
		ArrayList<ArrayList<String>> tweets = new ArrayList<ArrayList<String>>();
		try {
			while(request.next()){
				ArrayList<String> tweet = new ArrayList<String>();
				tweet.add(request.getString(1));
				tweet.add(request.getString(2));
				tweet.add(request.getString(3));
				tweets.add(tweet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tweets;
	}
	
	public ArrayList<ArrayList<String>> getFollowers(String user){
		Object[] params = new Object[1];
		params [0] = user;			
		ResultSet request = DB.getDb().select("SELECT id,name FROM Followers WHERE twitterUser=?",params);	
		ArrayList<ArrayList<String>> followers = new ArrayList<ArrayList<String>>();
		try {
			while(request.next()){
				ArrayList<String> follower = new ArrayList<String>();
				follower.add(request.getString(1));
				follower.add(request.getString(2));
				followers.add(follower);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return followers;
	}
	
	public ArrayList<ArrayList<String>> getFollows(String user){
		Object[] params = new Object[1];
		params [0] = user;			
		ResultSet request = DB.getDb().select("SELECT id,name FROM Follows WHERE twitterUser=?",params);	
		ArrayList<ArrayList<String>> follows = new ArrayList<ArrayList<String>>();
		try {
			while(request.next()){
				ArrayList<String> follow = new ArrayList<String>();
				follow.add(request.getString(1));
				follow.add(request.getString(2));
				follows.add(follow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return follows;
	}
	
	public HashMap<String, ArrayList<String>> getListak(String twitterUser){
		Object[] params = new Object[1];
		params[0] = twitterUser;
		ResultSet request = DB.getDb().select("SELECT listName, menberName FROM Lists WHERE twitterUser=?", params);
		HashMap<String, ArrayList<String>> list = new  HashMap<String, ArrayList<String>>();
		try {
			while(request.next()){
				if(list.containsKey(request.getString(1))){
					list.get(request.getString(1)).add(request.getString(2));
				}
				else{
					ArrayList<String> users = new ArrayList<String>();
					users.add(request.getString(2));
					list.put(request.getString(1), (users));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<ArrayList<String>> getMesages(String user){
		Object[] params = new Object[1];
		params [0] = user;			
		ResultSet request = DB.getDb().select("SELECT id,fromUser,toUser,mesage FROM DirectMesage WHERE twitterUser=?",params);	
		ArrayList<ArrayList<String>> mesages = new ArrayList<ArrayList<String>>();
		try {
			while(request.next()){
				ArrayList<String> mesage = new ArrayList<String>();
				mesage.add(request.getString(1));
				mesage.add(request.getString(2));
				mesage.add(request.getString(3));
				mesage.add(request.getString(4));
				mesages.add(mesage);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mesages;
	}


}
