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
import twitter4j.TwitterResponse;
import twitter4j.UserList;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import grafika.PinEnter;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URI;

import javax.swing.SingleSelectionModel;

import java.util.ArrayList;
import java.util.List;

import controller.DB;
import controller.TwitterController;
import controller.TwitterSesionController;

public class TwitterConect {
	
	Twitter twitter;
	
	public TwitterConect(){
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer("TSuJgYz97JvU53vCDmlH9o0TP", "WbB3ftTKbOtY9RW9Z6kozaE6fLW3kVkhOR0HCc1puwkRVldjap");
	}
	

	
	public void login(){
		
	            try {
	                // get request token.
	                // this will throw IllegalStateException if access token is already available
	                RequestToken requestToken = twitter.getOAuthRequestToken();
	                System.out.println("Got request token.");
	                System.out.println("Request token: " + requestToken.getToken());
	                System.out.println("Request token secret: " + requestToken.getTokenSecret());
	                AccessToken accessToken = null;
	
	                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	                while (null == accessToken) {
	                    //System.out.println("Open the following URL and grant access to your account:");
	                    //System.out.println(requestToken.getAuthorizationURL());
	                	try {
	                    	Desktop desktop = Desktop.getDesktop();
	            			desktop.browse(new URI(requestToken.getAuthorizationURL()));
	            		} catch (Exception e) {
	            			e.printStackTrace();
	            		}
//	                	System.out.print("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
	                	
	                	//String pin = br.readLine();
	                	PinEnter frame = new grafika.PinEnter();
	            		frame.setVisible(true);
	            		String pin = frame.getPin();
	            		System.out.println(pin);

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
	                System.out.println("Got access token.");
	                System.out.println("Access token: " + accessToken.getToken());
	                System.out.println("Access token secret: " + accessToken.getTokenSecret());
	                TwitterSesionController.getTwitterSesionController().newTwitterSession(twitter.getScreenName(),User.getUser().getId(), accessToken.getTokenSecret().toString(), accessToken.getToken().toString());
	            } catch (IllegalStateException ie) {
	                // access token is already available, or consumer key/secret is not set.
	                if (!twitter.getAuthorization().isEnabled()) {
	                    System.out.println("OAuth consumer key/secret is not set.");
	                    //System.exit(-1);
	                }
	            } catch (TwitterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
           
            //Status status = twitter.updateStatus("twitter4j proba2");
            //System.out.println("Successfully updated the status to [" + status.getText() + "].");
            //System.exit(0);
       
            
	}
	
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
	
	public void tokenakLortu(String twitterUser){
		String[] session = TwitterSesionController.getTwitterSesionController().getTwitterSession(twitterUser);
        if(!(session==null)){
        	AccessToken accesToken = new AccessToken(session[2], session[1]);
        	twitter.setOAuthAccessToken(accesToken);
        }
	}
	
	private Long gorde(List<Status> tweetak){
		String erab;
		Long azkenDeskarga = new Long(0);
		try {
			erab = twitter.getScreenName();
			for (Status status : tweetak) {//deskargatutako tweet zerrenda datubasean sartu
				TwitterController.getTwitterController().tweetaGorde(erab, status);
				azkenDeskarga = status.getId();
			}
			return azkenDeskarga;
		} catch (IllegalStateException | TwitterException e) {
			e.printStackTrace();
		}
		return azkenDeskarga;
	}

	public void tweetakDeskargatu(){
		List<Status> list = new ArrayList<Status>();
		int pageno = 1;
		Long azkenDeskarga = new Long(0);
		Long idBerri;
		Long helmuga = null;
		try {
			String user = twitter.getScreenName();
			idBerri = TwitterController.getTwitterController().tweetBerriZahar(user,"berri");//datubaseko tweet berriena
			try {
				if (idBerri==null) {//oraindik ez da deskargarik egin
					helmuga = new Long(1);
					while (true) {
						int size = list.size();
						Paging page = new Paging(pageno++, 20, new Long(1));
						list.addAll(twitter.getUserTimeline(page));
						if (list.size()==size) {
							azkenDeskarga = gorde(list);
							break;
						}
					}
				}
				else if (idBerri!=twitter.getUserTimeline(new Paging(1,1)).get(0).getId()) {//tweet berriak deskargatzeko
					helmuga = idBerri;
					while (true) {
						int size = list.size();
						Paging page = new Paging(pageno++, 20, idBerri);
						list.addAll(twitter.getUserTimeline(page));
						if (list.size()==size) {
							azkenDeskarga = gorde(list);
							break;
						}
					}
				}
				else {//tarteak deskargatu
					Long[] tarteak = TwitterController.getTwitterController().tarteaLortu(user, "MyTweets");
					if (tarteak==null) {
						System.out.println("Tweet guztiak deskargatuta dituzu");
					}else {
						helmuga = tarteak[1];
						TwitterController.getTwitterController().tarteaEzabatu(user, "MyTweets", tarteak[1]);
						while (true) {
							int size = list.size();
							Paging page = new Paging(pageno++, 20, tarteak[1], tarteak[0]);
							list.addAll(twitter.getUserTimeline(page));
							if (list.size()==size) {
								azkenDeskarga = gorde(list);
								break;
							}
						}
					}
				}
			} catch (TwitterException e) {//tweetak eta tarteak datubasean gorde
				System.out.println("application's rate limit, please wait 15m a retry");
				azkenDeskarga = gorde(list);
				TwitterController.getTwitterController().tarteaSartu(user, "MyTweets", azkenDeskarga-1, helmuga);
			}
		} catch (IllegalStateException | TwitterException e) {//lehena, getscreenname
			e.printStackTrace();
		}
	}
	
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
	
	public void getFavs(){
		getFavs(new Long("1"));
	}
	
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



