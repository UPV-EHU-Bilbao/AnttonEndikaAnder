package controller;

public class UserController {
	
	private static UserController instantzia = new UserController();
	
	
	private UserController(){}
	
	public static UserController getUserController(){
		return instantzia;
	}
	
	public boolean login(String userName, String password) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public boolean newUser(String userName, String password){
		boolean exist = false;
		
		return exist;
	}
}
