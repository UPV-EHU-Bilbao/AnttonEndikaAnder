package model;
import grafika.PinEnter;
import twitter4j.DirectMessage;
import twitter4j.IDs;
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.UserList;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import controller.TwitterController;
import controller.TwitterSesionController;

/**
 * Twitter api-arekin interakzioa
 */
public class TwitterConect {
	
	Twitter twitter;
	
	public TwitterConect(){
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer("TSuJgYz97JvU53vCDmlH9o0TP", "WbB3ftTKbOtY9RW9Z6kozaE6fLW3kVkhOR0HCc1puwkRVldjap");
	}
	
	/**
	 * twitter-eko apian logeatu eta lortutako tokenak datu basean gorde
	 */
	public void login(){
		
	            try {
	                RequestToken requestToken = twitter.getOAuthRequestToken();
	                AccessToken accessToken = null;
	                while (null == accessToken) {
	                	try {
	                    	Desktop desktop = Desktop.getDesktop();
	            			desktop.browse(new URI(requestToken.getAuthorizationURL()));
	            		} catch (Exception e) {
	            			e.printStackTrace();
	            		}
	                	PinEnter frame = new grafika.PinEnter();
	            		frame.setVisible(true);
	            		String pin = frame.getPin();

	                    try {
	                        if (pin.length() > 0) {
	                            accessToken = twitter.getOAuthAccessToken(requestToken, pin);
	                        } else {
	                            accessToken = twitter.getOAuthAccessToken(requestToken);
	                        }
	                    } catch (TwitterException te) {
	                        if (401 == te.getStatusCode()) {
	                            System.out.println("Unable to get the access token.");
	                        } else {
	                            te.printStackTrace();
	                        }
	                    }
	                }
	                TwitterSesionController.getTwitterSesionController().newTwitterSession(twitter.getScreenName(),User.getUser().getId(), accessToken.getTokenSecret().toString(), accessToken.getToken().toString());
	            } catch (IllegalStateException ie) {
	                if (!twitter.getAuthorization().isEnabled()) {
	                    System.out.println("OAuth consumer key/secret is not set.");
	                }
	            } catch (TwitterException e1) {
					e1.printStackTrace();
				}        
	}
	
	/**
	 * mesu bat tweeteatzen du
	 * @param statusMesage Tweeteatuko den mesua
	 */
	public void updateStatus(String statusMesage){
		Status status;
		try {
			status = twitter.updateStatus(statusMesage);
			System.out.println("Successfully updated the status to [" + status.getText() + "].");
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Erabiltzaile batek gordetako tokenak ezartzen ditu.
	 * @param twitterUser Twitter erabiltzaile izena
	 */
	public void tokenakLortu(String twitterUser){
		String[] session = TwitterSesionController.getTwitterSesionController().getTwitterSession(twitterUser);
        if(!(session==null)){
        	AccessToken accesToken = new AccessToken(session[2], session[1]);
        	twitter.setOAuthAccessToken(accesToken);
        }
	}
	
	/**
	 * Tweetak jaitsi eta gordetzen ditu
	 */
	public void getTwitts(){
		
        try {
        	ResponseList<Status> list = twitter.getUserTimeline();
        	for (Status status : list) {
        		TwitterController.getTwitterController().tweetaGorde(twitter.getScreenName(),status);
			}
        	getTwitts(Long.toString(list.get(list.size()-1).getId()-(new Long("1"))));
        } catch (TwitterException te) {
            System.out.println("application's rate limit, please wait 15m a retry");
        	//te.printStackTrace();
            //System.out.println("Failed to show status: " + te.getMessage());
        }
        
	}
	
	/**
	 * Tweetak jaitsi eta gordetzen ditu
	 * @param lastAdded deskargatu nahi den lehen tweeta
	 */
	public void getTwitts(String lastAdded){
	    try {
	    	ResponseList<Status> list = twitter.getUserTimeline(new Paging(1,20,Long.parseLong("1"),Long.parseLong(lastAdded)));
	    	if(list.size()!=0 && !list.get(0).equals(Long.parseLong(lastAdded))){
		    	for (Status status : list) {
		    		TwitterController.getTwitterController().tweetaGorde(twitter.getScreenName(),status);
				}
		    	getTwitts(Long.toString(list.get(list.size()-1).getId()-(new Long("1"))));
	    	}
	    } catch (TwitterException te) {
	    	System.out.println("application's rate limit, please wait 15m a retry");
	        //te.printStackTrace();
	        //System.out.println("Failed to show status: " + te.getMessage());
	    }
	    
	}
	
	/**
	 * Fav-ak jaitsi eta gordetzen ditu
	 */
	public void getFavs(){
		getFavs(new Long("1"));
	}
	
	/**
	 * Fav-ak jaitsi eta gordetzen ditu
	 * @param sinceId deskargatu nahi den azken tweeta
	 */
	public void getFavs(long sinceId){
		int pageno = 1;
		List<Status> statuses = new ArrayList<Status>();
		while (true) {
			try {
				int size = statuses.size();
				Paging page = new Paging(pageno++, 100, sinceId);
				statuses.addAll(twitter.getFavorites(page));
				if (statuses.size()==size) {
					for (Status status : statuses) {
						TwitterController.getTwitterController().favGorde(twitter.getScreenName(), status);
					}
					break;
				}
			} catch (TwitterException te) {
				//te.printStackTrace();
				System.out.println("Failed to get favorites: " + te.getMessage());
				//System.exit(-1);
			}
		}
		
	}
	
	/**
	 * Followerrak jaitsi eta gordetzen ditu
	 */
	public void getFollowers(){
		try {
            
            long cursor = -1;
            IDs ids;
            //System.out.println("Listing followers's ids.");
            do {
               
                ids = twitter.getFollowersIDs(cursor);
                
                for (long id : ids.getIDs()) {
                    //System.out.println(id);
                    //System.out.println(twitter.showUser(id).getScreenName());
                    TwitterController.getTwitterController().followerakGorde(Long.toString(id), twitter.showUser(id).getScreenName(), twitter.getScreenName());
                    
                }
            } while (((cursor = ids.getNextCursor()) != 0) && (ids.getIDs().length!=0));
            //System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get followers' ids: " + te.getMessage());
            //System.exit(-1);
        }
	}
	
	/**
	 * Followak jaitsi eta gordetzen ditu
	 */
	public void getFollows(){
		try {
            
            long cursor = -1;
            IDs ids;
            //System.out.println("Listing followers's ids.");
            do {
               
                ids = twitter.getFriendsIDs(cursor);
                
                for (long id : ids.getIDs()) {
                    //System.out.println(id);
                    //System.out.println(twitter.showUser(id).getScreenName());
                    TwitterController.getTwitterController().followakGorde(Long.toString(id), twitter.showUser(id).getScreenName(), twitter.getScreenName());
                    
                }
                System.out.println(ids.getIDs().length);
            } while (((cursor = ids.getNextCursor()) != 0) && (ids.getIDs().length!=0));
            //System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get followers' ids: " + te.getMessage());
            //System.exit(-1);
        }
	}
	
	/**
	 * Listak jaitsi eta gordetzen ditu
	 */
	public void getLists(){
		try {
            ResponseList<UserList> lists = twitter.getUserLists(twitter.getScreenName());
            for (UserList list : lists) {
                //System.out.println("id:" + list.getId() + ", name:" + list.getName() + ", description:" + list.getDescription() + ", slug:" + list.getSlug() + "");

                //get members of a list
                long cursor = -1;
                PagableResponseList<twitter4j.User> usres;
                do {
                    usres = twitter.getUserListMembers(list.getId(), cursor);
                    for (twitter4j.User lista : usres) {
                        //System.out.println("@" + lista.getScreenName());
                    	TwitterController.getTwitterController().listakGorde(list.getId(),list.getName(),lista.getScreenName(),twitter.getScreenName());
                    }
                } while (((cursor = usres.getNextCursor()) != 0) && (!usres.isEmpty()));
                
            }
            
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to list the lists: " + te.getMessage());
            //System.exit(-1);
        }
	}
	
	/**
	 * Mezu zuzenak jaitsi eta gordetzen ditu
	 */
	public void getDirectMessages(){
		try {
			//niri bidaldutakoak
            Paging page = new Paging(1);
            List<DirectMessage> messages;
            do {
                messages = twitter.getDirectMessages(page);
                for (DirectMessage message : messages) {
                    //System.out.println("From: @" + message.getSenderScreenName() + " id:" + message.getId() + " - " + message.getText());
                	TwitterController.getTwitterController().mezuaGorde(Long.toString(message.getId()), message.getSenderScreenName(), twitter.getScreenName(), message.getText(), twitter.getScreenName());
                }
                page.setPage(page.getPage() + 1);
            } while (messages.size() > 0 /*&& paging.getPage() < 10*/);
            
            //nik bidalitakoak
            page = new Paging(1);
            do {
                messages = twitter.getSentDirectMessages(page);
                for (DirectMessage message : messages) {
                    //System.out.println("To: @" + message.getRecipientScreenName() + " id:" + message.getId() + " - " + message.getText());
                	TwitterController.getTwitterController().mezuaGorde(Long.toString(message.getId()), twitter.getScreenName(), message.getRecipientScreenName(), message.getText(), twitter.getScreenName());
                }
                page.setPage(page.getPage() + 1);
            } while (messages.size() > 0 /*&& page.getPage() < 10*/);
            
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get messages: " + te.getMessage()); 
        }
	}
	
}



