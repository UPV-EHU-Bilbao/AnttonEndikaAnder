
package model;

import java.util.HashMap;
import java.util.LinkedList;

import controller.UserController;

public class User {
	
	private String erabiltzaileIzena;
	private String pasahitza;
	private int userId;
	private int id;
	private HashMap<String, TwitterConect> twitterAcounts;
	private static User instantzia = new User();
	
	public User(){
		twitterAcounts = new HashMap<String, TwitterConect>();
	}
	
	public static User getUser(){
		return instantzia;
	}
	
	public int login(String pErabiltzaileIzena, String pPasahitza) {
		int logged=0;
		logged = UserController.getUserController().login(pErabiltzaileIzena, new HashSha512(pPasahitza).getHash());
		if (logged!=-1){
			erabiltzaileIzena = pErabiltzaileIzena;
			pasahitza = new HashSha512(pPasahitza).getHash();
			id=logged;
		}
		
		return logged;
	}
	
	public int newUser(String pErabiltzaileIzena, String pPasahitza){
		int exist;
		UserController.getUserController().newUser(pErabiltzaileIzena, new HashSha512(pPasahitza).getHash());
		exist =login(pErabiltzaileIzena, new HashSha512(pPasahitza).getHash());
		if (exist != -1){
			erabiltzaileIzena = pErabiltzaileIzena;
			pasahitza = new HashSha512(pPasahitza).getHash();
			this.id=exist;
		}
		
		return exist;
	}
	
	public LinkedList<String> getTwitterUsers(){
		return UserController.getUserController().getTwitterUsers(this.id);
	}

	public String getId() {
		// TODO Auto-generated method stub
		return String.valueOf(this.id);
	}
	public String getUserId() {
		// TODO Auto-generated method stub
		return String.valueOf(this.userId);
	}
	
	
}
