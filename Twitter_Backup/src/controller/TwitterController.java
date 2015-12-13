package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import twitter4j.Status;


public class TwitterController {

	private static TwitterController instantzia=new TwitterController();
	private long azkenTweetId;
	private Long azkenFavId;
	private long azkenFollowers;
	private long azkenFollows;
	private long azkenMezua;

	private TwitterController(){	
		azkenTweetId = new Long(0);
		azkenFavId=new Long(0);
		azkenFollowers = new Long(0);
		azkenFollows = new Long(0);
		azkenMezua = new Long(0);
	}

	public static TwitterController getTwitterController(){
		return instantzia;
	}
	
	public Long tweetBerriZahar(String tUser, String posizioa){
		//datubasean sartutako id-a zaharrena edo berriena itzultzen du deskargak kudeatzeko
		Object[] params = new Object[1];
		params[0] = tUser;
		//params [1] = "DESC";
		String mota = "DESC";
		if (!posizioa.equals("berri")) {
			//params[1] = "ASC";
			mota = "ASC";
		}
		try {			
			ResultSet request = DB.getDb().select("SELECT id FROM MyTweets WHERE twitterUser=? ORDER BY id "+mota+" LIMIT 1",params);	
			request.next();
			return new Long(request.getLong(1));
		} catch (Exception e) {
			System.out.println("Error:  "+e);
			e.printStackTrace();
		}
		//return (Long)null;
		return null;
	}

//	public Long getAzkenTweetId(String taula){
//		//datubasean sartutako azken id-a itzultzen du gehiago egotekotan deskarga bertatik jarraitzeko
//		try {			
//			Object[] params = new Object[0];
//			ResultSet request = DB.getDb().select("SELECT id FROM MyTweets ORDER BY id DESC LIMIT 1",params);	
//			request.next();
//			return new Long(request.getLong(1));
//
//		} catch (Exception e) {
//			System.out.println("Error:  "+e);
//		}
//		return null;
//	}
	
	public ArrayList<String> tweetakIkusi(String tUser, boolean hasieratu) throws SQLException {
		//pantailaratutako azken id-tik abiaratuta beste 20 hartzen ditu
		if(hasieratu){
			azkenTweetId=0;
		}
		ResultSet request = null;
		if (azkenTweetId!=0) {
			Object[] params = new Object[2];
			params[0]=Long.toString(azkenTweetId);
			params[1]=tUser;
			request = DB.getDb().select("SELECT id,mesage FROM MyTweets WHERE id < ? AND twitterUser=? ORDER BY id DESC LIMIT 20",params);
		}
		else {
			Object[] params = new Object[1];
			params[0]=tUser;			
			request = DB.getDb().select("SELECT id,mesage FROM MyTweets WHERE twitterUser=? ORDER BY id DESC LIMIT 20", params);
		}
		ArrayList<String> st=new ArrayList<String>();
		while(request.next()){
			st.add(request.getString(2));
			azkenTweetId = request.getLong(1);
		}
		return st;
	}

//	public ArrayList<String> lehentweetakIkusi(String tUser) throws SQLException {
//		//Honek lehen 20-ak hartzen ditu (datubasetik), hurrengoak hartzeko beste sql sententzia bat erabili behar delako
//		//ResultSet request = Dd.getDd().select("SELECT id,mesage FROM MyTweets WHERE twitterUser='"+tUser+"' ORDER BY id DESC LIMIT 20");
//		Object[] params = new Object[1];
//		params[0]=tUser;
//
//		ResultSet request = DB.getDb().select("SELECT id,mesage FROM MyTweets WHERE twitterUser=? ORDER BY id DESC LIMIT 20", params);
//		ArrayList<String> st=new ArrayList<String>();
//
//		while(request.next()){
//			st.add(request.getString(2));
//			azkenTweetId = request.getLong(1);
//		}
//		return st;
//	}
//
//	public ArrayList<String> tweetakIkusi(String tUser) throws SQLException {
//		//pantailaratutako azken id-tik abiaratuta beste 20 hartzen ditu
//		Object[] params = new Object[2];
//		params[0]=Long.toString(azkenTweetId);
//		params[1]=tUser;
//		ResultSet request = DB.getDb().select("SELECT id,mesage FROM MyTweets WHERE id < ? AND twitterUser=? ORDER BY id DESC LIMIT 20",params);
//		ArrayList<String> st=new ArrayList<String>();
//		while(request.next()){
//			st.add(request.getString(2));
//			azkenTweetId = request.getLong(1);
//		}
//		return st;
//	}

	public ArrayList<String[]> favIkusi(String tUser, boolean hasieratu) throws SQLException {
		//pantailaratutako azken id-tik abiaratuta beste 20 hartzen ditu
		if(hasieratu){
			azkenFavId=new Long(0);
		}
		ResultSet request=null;
		if(!azkenFavId.equals(new Long(0))){
			Object[] params = new Object[2];
			params[0]=azkenFavId.toString();
			params[1]=tUser;
			request = DB.getDb().select("SELECT id,mesage,name FROM Fav WHERE id < ? AND twitterUser=? ORDER BY id DESC LIMIT 20",params);
		}else{
			Object[] params = new Object[1];
			params[0]=tUser;
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

//	public ArrayList<String[]> lehenFavkIkusi(String tUser) throws SQLException {
//		//Honek lehen 20-ak hartzen ditu (datubasetik), hurrengoak hartzeko beste sql sententzia bat erabili behar delako
//		//ResultSet request = Dd.getDd().select("SELECT id,mesage FROM MyTweets WHERE twitterUser='"+tUser+"' ORDER BY id DESC LIMIT 20");
//		Object[] params = new Object[1];
//		params[0]=tUser;
//
//		ResultSet request = DB.getDb().select("SELECT id,mesage,name FROM Fav WHERE twitterUser=? ORDER BY id DESC LIMIT 20", params);
//		ArrayList<String[]> st=new ArrayList<String[]>();
//		
//		while(request.next()){
//			String[] stlis=new String[2];
//			stlis[0]=request.getString(2);
//			stlis[1]="@"+request.getString(3);
//			st.add(stlis);
//			azkenFavId = request.getLong(1);
//		}
//		return st;
//	}

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
		//System.out.println(params[0]+"   "+params[1]+"    "+params[2]);
		DB.getDb().insert("INSERT INTO Followers(id, name, twitterUser)VALUES(?,?,?)", params);
	}
	
	public ArrayList<String> followerakIkusi(String tUser, boolean hasieratu){
		ResultSet request=null;
		if(hasieratu){
			azkenFollowers=new Long(0);
		}
		if(azkenFollowers!=new Long(0)){
			Object[] params = new Object[2];
			params[0]=Long.toString(azkenFollowers);
			params[1]=tUser;
			request = DB.getDb().select("SELECT id,name FROM Followers WHERE id < ? AND twitterUser=? ORDER BY id DESC LIMIT 20",params);
		}else{
			Object[] params = new Object[1];
			params[0]=tUser;
			request = DB.getDb().select("SELECT id,name FROM Followers WHERE twitterUser=? ORDER BY id DESC LIMIT 20", params);
		}
		ArrayList<String> st=new ArrayList<String>();
		
		try {
			while(request.next()){
				azkenFollowers = request.getLong(1);
				st.add("@"+request.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return st;
	}
	
	public void followakGorde(String id, String name, String user){
		Object[] params = new Object[3];
		params[0] = id;
		params[1] = name;
		params[2] = user;
		//System.out.println(params[0]+"   "+params[1]+"    "+params[2]);
		DB.getDb().insert("INSERT INTO Follows(id, name, twitterUser)VALUES(?,?,?)", params);
	}
	
	public ArrayList<String> followakIkusi(String tUser, boolean hasieratu){
		ResultSet request=null;
		if(hasieratu){
			azkenFollows=0;
		}
		if(azkenFollows!=new Long(0)){
			Object[] params = new Object[2];
			params[0]=Long.toString(azkenFollows);
			params[1]=tUser;
			request = DB.getDb().select("SELECT id,name FROM Follows WHERE id < ? AND twitterUser=? ORDER BY id DESC LIMIT 20",params);
		}else{
			Object[] params = new Object[1];
			params[0]=tUser;
			request = DB.getDb().select("SELECT id,name FROM Follows WHERE twitterUser=? ORDER BY id DESC LIMIT 20", params);
		}
		ArrayList<String> st=new ArrayList<String>();
		
		try {
			while(request.next()){
				azkenFollows = request.getLong(1);
				st.add("@"+request.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return st;
	}
	
	public void listakGorde(long id, String listName, String menberOfList, String twitterUser){
		Object[] params = new Object[4];
		params[0] = Long.toString(id);
		params[1] = listName;
		params[2] = menberOfList;
		params[3] = twitterUser;
		DB.getDb().insert("INSERT INTO Lists(id, listName, menberName, twitterUser)VALUES(?,?,?,?)", params);
	}
	
	public HashMap<String, ArrayList<String>> listakIkusi(String twitterUser){
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
	
	public void mezuaGorde(String id, String from, String to, String mesage, String twitterUser){
		Object[] params = new Object[5];
		params[0] = id;
		params[1] = from;
		params[2] = to;
		params[3] = mesage;
		params[4] = twitterUser;
		DB.getDb().insert("INSERT INTO DirectMesage(id, fromUser, toUser, mesage, twitterUser)VALUES(?,?,?,?,?)", params);
	}
	
	public ArrayList<String[]> mezuakIkusi(String tUser, boolean hasieratu){
		ResultSet request=null;
		if (hasieratu){
			azkenMezua=0;
		}
		if(azkenMezua!=new Long(0)){
			Object[] params = new Object[2];
			params[0]=Long.toString(azkenMezua);
			params[1]=tUser;
			request = DB.getDb().select("SELECT id,fromUser,toUser,mesage FROM DirectMesage WHERE id < ? AND twitterUser=? ORDER BY id DESC LIMIT 20",params);
		}else{
			Object[] params = new Object[1];
			params[0]=tUser;
			request = DB.getDb().select("SELECT id,fromUser,toUser,mesage FROM DirectMesage WHERE twitterUser=? ORDER BY id DESC LIMIT 20", params);
		}
		ArrayList<String[]> st=new ArrayList<String[]>();
		
		try {
			while(request.next()){
				azkenMezua = request.getLong(1);
				String[] mezua = new String[3];
				mezua[0] = request.getString(2);
				mezua[1] = request.getString(3);
				mezua[2] = request.getString(4);
				st.add(mezua);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return st;
	}
	
	public void tarteaSartu(String tUser, String taula, Long etena, Long helmuga){
		Object[] params = new Object[4];
		params[0]=taula;
		params[1]=etena.toString();
		params[2]=helmuga.toString();
		params[3]=tUser;
		DB.getDb().insert("INSERT INTO tarteak(mota, etenpuntua, helmuga, UserTwitter) values(?, ?, ?, ?)", params);
	}
	
	public void tarteaEzabatu(String tUser, String taula, Long etena){
		Object[] params = new Object[3];
		params[0]=tUser;
		params[1]=taula;
		params[2]=etena.toString();
		DB.getDb().update("DELETE FROM tarteak WHERE twitterUser=? AND mota=? AND etenpuntua=?", params);
	}
	
	public Long[] tarteaLortu(String tUser, String taula){
		ResultSet request=null;
		Object[] params = new Object[2];
		params[0]= tUser;
		params[1] = taula;
		request = DB.getDb().select("SELECT etenpuntua, helmuga FROM tarteak WHERE UserTwitter=? AND mota=? ORDER BY etenpuntua ASC", params);
		Long[] st = new Long[2];
		try {
			request.next();
			st[0] = request.getLong(0);
			st[1] = request.getLong(1);
			return st;
			
		} catch (SQLException e) {
			//e.printStackTrace();
			return null;
		}
		
	}

}
