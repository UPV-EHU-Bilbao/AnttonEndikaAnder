package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
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
		ResultSet rd =Dd.getDd().select("SELECT id FROM TwitterBackup.UserLocal where user='"+userName+"' and password='"+password+"'");
		try {
			rd.next();
			int id=Integer.parseInt(rd.getString(1));
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return -1;
		}
		
		
	}
	
	public int newUser(String userName, String password){
		//erabiltzailea ezin da existitu 
		int exist = 0;
		Dd.getDd().insert("INSERT UserLocal (`user`, `password`) VALUES('"+userName+"', '"+password+"')");
		return exist;
	}

	public LinkedList<String> getTwitterUsers(int id) {
		ResultSet rd=Dd.getDd().select("SELECT twitterUser FROM UserTwitter where userId='"+id+"'");
		LinkedList<String> lk =new LinkedList<String>();
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
