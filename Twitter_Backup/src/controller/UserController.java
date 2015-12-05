package controller;

import java.awt.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.omg.CORBA.Request;

import model.User;

public class UserController {
	
	private static UserController instantzia = new UserController();
	
	
	private UserController(){}
	
	public static UserController getUserController(){
		return instantzia;
	}
	
	public int login(String userName, String password) {
		// TODO Auto-generated method stub
		//ResultSet rd =Dd.getDd().select("SELECT id FROM TwitterBackup.UserLocal where user='"+userName+"' and password='"+password+"'");
		Object[] params = new Object[2];
		params[0]=userName;
		params[1]=password;
		ResultSet rd =DB.getDb().select("SELECT id FROM UserLocal where user=? and password=?",params);
		try {
			rd.next();
			int id=Integer.parseInt(rd.getString(1));
			System.out.println(id);
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return -1;
		}
		
		
	}
	
	public int newUser(String userName, String password){
		//erabiltzailea ezin da existitu. izen bereko bi erabiltzaile edo gehiago egon daitezke baina pasahitz ezberdinak eduki behar dituzte
		int exist = 0;
		Object[] params = new Object[2];
		params[0]=userName;
		params[1]=password;
		DB.getDb().insert("INSERT into UserLocal (`user`, `password`) VALUES(?,?)", params);
		return exist;
	}

	public ArrayList<String> getTwitterUsers(int id) {
		//ResultSet rd=Dd.getDd().select("SELECT twitterUser FROM UserTwitter where userId='"+id+"'");
		Object[] params = new Object[1];
		params[0]=id;
		ResultSet rd=DB.getDb().select("SELECT twitterUser FROM UserTwitter where userId=?",params);
		ArrayList<String> lk =new ArrayList<String>();
		//linked lis da endikak esan duelako
		try {
			while(rd.next()){
				lk.add(rd.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lk;
	}
}
