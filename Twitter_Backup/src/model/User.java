package model;

import java.util.ArrayList;
import controller.UserController;

/**
 * Erabiltzaileak kudeatzeko klasea
 */
public class User {
	
	private String erabiltzaileIzena;
	private int userId;
	private int id;
	private static User instantzia = new User();
	
	private User(){

	}
	
	public static User getUser(){
		return instantzia;
	}
	
	/**
	 * Erabiltzailea aplikazioan saioa hasi
	 * @param pErabiltzaileIzena Erabiltzailearen izena
	 * @param pPasahitza Erabiltzailearen pasahitza
	 * @return Saioa hasu badu bueltatzen du
	 */
	public int login(String pErabiltzaileIzena, String pPasahitza) {
		int logged=0;
		logged = UserController.getUserController().login(pErabiltzaileIzena, new HashSha512(pPasahitza).getHash());
		if (logged!=-1){
			erabiltzaileIzena = pErabiltzaileIzena;
			new HashSha512(pPasahitza).getHash();
			id=logged;
		}
		
		return logged;
	}
	
	/**
	 * Erabiltzaile berria erregistratu
	 * @param pErabiltzaileIzena Erabiltzailearen izena
	 * @param pPasahitza Erabiltzailearen pasahitza
	 * @return Erabiltzaile berriaren erregistroa zuzena izan den ala ez
	 */
	public int newUser(String pErabiltzaileIzena, String pPasahitza){
		int exist;
		UserController.getUserController().newUser(pErabiltzaileIzena, new HashSha512(pPasahitza).getHash());
		exist =login(pErabiltzaileIzena, new HashSha512(pPasahitza).getHash());
		if (exist != -1){
			erabiltzaileIzena = pErabiltzaileIzena;
			new HashSha512(pPasahitza).getHash();
			this.id=exist;
		}
		
		return exist;
	}
	
	public ArrayList<String> getTwitterUsers(){
		return UserController.getUserController().getTwitterUsers(this.id);
	}

	public String getId() {
		return String.valueOf(this.id);
	}
	public String getUserId() {
		return String.valueOf(this.userId);
	}
	
	
}
