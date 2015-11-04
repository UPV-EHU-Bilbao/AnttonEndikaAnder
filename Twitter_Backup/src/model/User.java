package model;

import java.util.HashMap;

public class User {
	private String erabiltzaileIzena;
	private String pasahitza;
	private HashMap<String, TwitterConect> twitterAcounts;
	
	public User(String pErabiltzaileIzena, String pPasahitza){
		erabiltzaileIzena = pErabiltzaileIzena;
		pasahitza = new HashSha512(pPasahitza).getHash();
		twitterAcounts = new HashMap<String, TwitterConect>();
	}
	
}
