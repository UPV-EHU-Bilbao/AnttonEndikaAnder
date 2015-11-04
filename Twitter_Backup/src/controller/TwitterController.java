package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Stack;

import twitter4j.Status;

public class TwitterController {

	private static TwitterController instantzia=new TwitterController();
	
	private TwitterController(){
		
	}
	
	public static TwitterController getTwitterController(){
		return instantzia;
	}
	
	public Long getAzkenId(){
		try {
			
			ResultSet request = Dd.getDd().select("SELECT id FROM MyTweets ORDER BY id DESC LIMIT 1");			
			request.next();
			return new Long(request.getLong(1));
			
		} catch (Exception e) {
			System.out.println("Error:  "+e);
		}
		return new Long(-1);		//taula hutsik dagoenean
	}
	
	public Stack<String> tweetakIkusi(Long lehen, Long bigarren) throws SQLException {
		ResultSet request = Dd.getDd().select("SELECT id,mesage FROM MyTweets WHERE id BETWEEN "+getAzkenId()+" AND "+(getAzkenId()+2000)+" ORDER BY id DESC");
//		ResultSet request = this.select("SELECT id,mesage FROM MyTweets ORDER BY id DESC LIMIT 20");
//		String emaitza = null;
//		request.getAsciiStream(emaitza);
//		return emaitza;
		Stack<String> st=new Stack<String>();
		while(request.next()){
			st.add(request.getString(2));
			}
		return st;
	}
	
	public void tweetaGorde(String userName, Status status){
		//System.out.println(userName);
		//System.out.println(status.getId()+": "+status.getText()+"    "+status.getCreatedAt());
		//System.out.println("INSERT INTO TwitterBackup.MyTweets(`id`,`mesage`,`name`,`twitterUser`)VALUES("+status.getId()+",'"+status.getText() +"','"+status.getUser().getScreenName()+"','"+userName+"')");
		Dd.getDd().insert("INSERT INTO TwitterBackup.MyTweets(id,mesage,name,twitterUser)VALUES('"+status.getId()+"','"+status.getText() +"','"+status.getUser().getScreenName()+"','"+userName+"')");		
	}
	
	
	
	
}
