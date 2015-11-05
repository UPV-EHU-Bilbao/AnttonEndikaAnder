package model;

import java.util.HashMap;

import controller.UserController;

public class User {
	
	private String erabiltzaileIzena;
	private String pasahitza;
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
	
	public boolean newUser(String pErabiltzaileIzena, String pPasahitza){
		boolean exist;
		exist = UserController.getUserController().newUser(pErabiltzaileIzena, new HashSha512(pPasahitza).getHash());
		if (exist == false){
			erabiltzaileIzena = pErabiltzaileIzena;
			pasahitza = new HashSha512(pPasahitza).getHash();
		}
		
		return exist;
	}
	
	public void getTwitterUsers(){
		UserController.getUserController().getTwitterUsers(id);
	}
	
	
}
