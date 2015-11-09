package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TwitterSesionController {

	private static TwitterSesionController instantzia = new TwitterSesionController();
	
	
	private TwitterSesionController(){
		
	}
	
	public static TwitterSesionController getTwitterSesionController(){
		return instantzia;
	}
	
	
	public String[] getTwitterSession(){
		String[] session = null;
		try {
			ResultSet request = Dd.getDd().select("SELECT twitterUser,tokenSecret,token FROM UserTwitter WHERE tokenSecret!=null AND token!=null");
			if (request.next()==true){
				session = new String[3];
				session[0] = request.getString(1);
				session[1] = request.getString(2);
				session[2] = request.getString(3);
			}
		} catch (SQLException e) {
			System.out.println("Error:  "+e);
		}
		
		return session;
	}
	
	public void closeTwitterSession(String twitterUser){
		Dd.getDd().update("UPDATE UserTwitter SET tokenSecret=null, token=null WHERE twitterUser='"+twitterUser+"'");
	}
	
	public void newTwitterSession(String tUser,String lUser, String tokenSecret, String token){
		//db-ko usertwiter entitatea ahula izan beharko zen ezin delako egon twitterreko erabiltzailerik
		//aplikazioko erabiltzailerik gabe horrela lortzen duguna da beti biak lotuta egotea.
		//dagoen moduan utzita twitterreko erabiltzailak ez ditu aplikazioko erbiltzaileekin lotzen
		Dd.getDd().insert("INSERT INTO UserTwitter(twitterUser,tokenSecret,token,`userId`)VALUES('" + tUser + "','" + tokenSecret + "','" + token + "'," +lUser+ ")");
	}	
	
}
