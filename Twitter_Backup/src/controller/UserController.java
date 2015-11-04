package controller;

public class UserController {
	
private static UserController instantzia = new UserController();
	
	
	private UserController(){}
	
	public static UserController getUserController(){
		return instantzia;
	}
	
	public boolean login(String text, String text2) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void newUser(String userName, String password){
		
	}
}
