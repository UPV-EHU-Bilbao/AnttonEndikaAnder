package model;

import java.util.HashMap;

import controller.UserController;

public class User {
	
	private String erabiltzaileIzena;
	private String pasahitza;
	private HashMap<String, TwitterConect> twitterAcounts;
	private static User instantzia = new User();
	
	public User(){
		twitterAcounts = new HashMap<String, TwitterConect>();
	}
	
	public static User getUser(){
		return instantzia;
	}
	
	public boolean login(String pErabiltzaileIzena, String pPasahitza) {
		boolean logged;
		logged = UserController.getUserController().login(pErabiltzaileIzena, pPasahitza);
		if (logged == true){
			erabiltzaileIzena = pErabiltzaileIzena;
			pasahitza = new HashSha512(pPasahitza).getHash();
		}
		
		return logged;
	}
	
	public boolean newUser(String pErabiltzaileIzena, String pPasahitza){
		boolean exist;
		exist = UserController.getUserController().newUser(pErabiltzaileIzena, pPasahitza);
		if (exist == false){
			erabiltzaileIzena = pErabiltzaileIzena;
			pasahitza = new HashSha512(pPasahitza).getHash();
		}
		
		return exist;
	}
	
	
}
