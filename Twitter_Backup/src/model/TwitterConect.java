package model;
import grafika.InformazioMezua;
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
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;

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
	                            JDialog info =new InformazioMezua("Unable to get the access token.");
	            				info.setVisible(true);
	                        } else {
	                            te.printStackTrace();
	                        }
	                    }
	                }
	                TwitterSesionController.getTwitterSesionController().newTwitterSession(twitter.getScreenName(),User.getUser().getId(), accessToken.getTokenSecret().toString(), accessToken.getToken().toString());
	            } catch (IllegalStateException ie) {
	                if (!twitter.getAuthorization().isEnabled()) {
	                    JDialog info =new InformazioMezua("OAuth consumer key/secret is not set.");
        				info.setVisible(true);
	                }
	            } catch (TwitterException e1) {
					e1.printStackTrace();
				}        
	}
	
	/**
	 * mezu bat tweeteatzen du
	 * @param statusMesage Tweeteatuko den mezua
	 */
	public void updateStatus(String statusMesage){
		Status status;
		try {
			status = twitter.updateStatus(statusMesage);
			System.out.println("Successfully updated the status to [" + status.getText() + "].");
		} catch (TwitterException e) {
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
	 * Deskargatutako tweetak datubasean gordetezen ditu eta azken tweet-aren id-a itzultzen du, hurrego deskarga bertatik jarraitzeko
	 * @param elementua Tweet-a eta dagokion informazio(id, erabiltzaile, etab) zerrenda
	 * @return Azken tweet-aren id-a itzultzen du
	 */
	private Long gorde(List<Status> elementua,String taula){
		String erab;
		Long azkenDeskarga = new Long(0);
		try {
			erab = twitter.getScreenName();
			for (Status status : elementua) {//deskargatutako elementu zerrenda datubasean sartu
				if (taula.equals("MyTweets")) {
					TwitterController.getTwitterController().tweetaGorde(erab, status);
					azkenDeskarga = status.getId();
				}
				else if (taula.equals("Fav")) {
					TwitterController.getTwitterController().favGorde(erab, status);
					azkenDeskarga = status.getId();
				}
			}
			return azkenDeskarga;
		} catch (IllegalStateException | TwitterException e) {
			e.printStackTrace();
		}
		return azkenDeskarga;
	}
	
	/**
	 * Tweet-ak deskargatzen ditu. Berrienak lehenengo eta aurreko saioan jeitsi gabe utzitakoak ondoren.  
	 */
	public void tweetakDeskargatu(){
		List<Status> list = new ArrayList<Status>();
		int pageno = 1;
		Long azkenDeskarga = new Long(0);
		Long idBerri;
		Long helmuga = null;
		try {
			String user = twitter.getScreenName();
			idBerri = TwitterController.getTwitterController().tweetBerriZahar("MyTweets",user,"berri");//datubaseko tweet berriena
			try {
				if (idBerri==null) {//oraindik ez da deskargarik egin
					helmuga = new Long(1);
					while (true) {
						int size = list.size();
						Paging page = new Paging(pageno++, 20, new Long(1));
						//list.addAll(twitter.getUserTimeline(page));
						list = twitter.getUserTimeline(page);
						azkenDeskarga = gorde(list,"MyTweets");
						if (list.size()==0) {
							//azkenDeskarga = gorde(list);
							break;
						}
					}
				}
				else if (idBerri!=twitter.getUserTimeline(new Paging(1,1)).get(0).getId()) {//tweet berriak deskargatzeko
					helmuga = idBerri;
					while (true) {
						helmuga=idBerri;
						int size = list.size();
						Paging page = new Paging(pageno++, 20, idBerri+1);
						//list.addAll(twitter.getUserTimeline(page));
						list = twitter.getUserTimeline(page);
						azkenDeskarga = gorde(list,"MyTweets");
						if (list.size()==0) {
							//azkenDeskarga = gorde(list);
							break;
						}
					}
				}
				else {//tarteak deskargatu
					Long[] tarteak = TwitterController.getTwitterController().tarteaLortu(user, "MyTweets");
					if (tarteak==null) {
					}else {
						helmuga = tarteak[1];
						//azkenDeskarga = tarteak[0]; lehen buelta baino lehenago eten daiteke?
						TwitterController.getTwitterController().tarteaEzabatu(user, "MyTweets", tarteak[1]);
						while (true) {
							int size = list.size();
							Paging page = new Paging(pageno++, 20, tarteak[1], tarteak[0]);
							//list.addAll(twitter.getUserTimeline(page));
							list = twitter.getUserTimeline(page);
							azkenDeskarga = gorde(list,"MyTweets");
							if (list.size()==0) {
								//azkenDeskarga = gorde(list);
								break;
							}
						}
					}
				}
			} catch (TwitterException e) {//tweetak eta tarteak datubasean gorde
				JDialog info =new InformazioMezua("application's rate limit, please wait 15m a retry");
				info.setVisible(true);
				if (azkenDeskarga!=0)
					TwitterController.getTwitterController().tarteaSartu(user, "MyTweets", azkenDeskarga-1, helmuga);
			}
		} catch (IllegalStateException | TwitterException e) {//lehena, getscreenname
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Favorite-ak deskargatzen ditu. Lehenengo berrienak eta gero lehen saioan jeitsi gabe utzi dituen tarteak. 
	 */
	public void favDeskargatu(){
		List<Status> list = new ArrayList<Status>();
		int pageno = 1;
		Long azkenDeskarga = new Long(0);
		Long idBerri;
		Long helmuga = null;
		try {
			String user = twitter.getScreenName();
			idBerri = TwitterController.getTwitterController().tweetBerriZahar("Fav",user,"berri");//datubaseko tweet berriena
			try {
				if (idBerri==null) {//oraindik ez da deskargarik egin
					helmuga = new Long(1);
					while (true) {
						int size = list.size();
						Paging page = new Paging(pageno++, 20, new Long(1));
						//list.addAll(twitter.getUserTimeline(page));
						list = twitter.getFavorites(page);
						azkenDeskarga = gorde(list,"Fav");
						if (list.size()==0) {
							//azkenDeskarga = gorde(list);
							break;
						}
					}
				}
				else if (idBerri!=twitter.getFavorites(new Paging(1,1)).get(0).getId()) {//tweet berriak deskargatzeko
					helmuga = idBerri;
					while (true) {
						helmuga=idBerri;
						int size = list.size();
						Paging page = new Paging(pageno++, 20, idBerri+1);
						//list.addAll(twitter.getUserTimeline(page));
						list = twitter.getFavorites(page);
						azkenDeskarga = gorde(list,"Fav");
						if (list.size()==0) {
							//azkenDeskarga = gorde(list);
							break;
						}
					}
				}
				else {//tarteak deskargatu
					Long[] tarteak = TwitterController.getTwitterController().tarteaLortu(user, "Fav");
					if (tarteak==null) {
					}else {
						helmuga = tarteak[1];
						TwitterController.getTwitterController().tarteaEzabatu(user, "Fav", tarteak[1]);
						while (true) {
							int size = list.size();
							Paging page = new Paging(pageno++, 20, tarteak[1], tarteak[0]);
							//list.addAll(twitter.getUserTimeline(page));
							list = twitter.getFavorites(page);
							azkenDeskarga = gorde(list,"Fav");
							if (list.size()==0) {
								//azkenDeskarga = gorde(list);
								break;
							}
						}
					}
				}
			} catch (TwitterException e) {//tweetak eta tarteak datubasean gorde
				JDialog info =new InformazioMezua("application's rate limit, please wait 15m a retry");
				info.setVisible(true);
				if (azkenDeskarga!=0)
					TwitterController.getTwitterController().tarteaSartu(user, "Fav", azkenDeskarga-1, helmuga);
			}
		} catch (IllegalStateException | TwitterException e) {//lehena, getscreenname
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Followerrak jaitsi eta gordetzen ditu
	 */
	public void getFollowers(){
		try {
            
            long cursor = -1;
            IDs ids;
            do {
               
                ids = twitter.getFollowersIDs(cursor);
                
                for (long id : ids.getIDs()) {
                 TwitterController.getTwitterController().followerakGorde(Long.toString(id), twitter.showUser(id).getScreenName(), twitter.getScreenName());
                }
            } while (((cursor = ids.getNextCursor()) != 0) && (ids.getIDs().length!=0));
        } catch (TwitterException te) {
            JDialog info =new InformazioMezua("application's rate limit, please wait 15m a retry");
			info.setVisible(true);
        }
	}
	
	/**
	 * Followak jaitsi eta gordetzen ditu
	 */
	public void getFollows(){
		try {
            
            long cursor = -1;
            IDs ids;
            do {
               
                ids = twitter.getFriendsIDs(cursor);
                
                for (long id : ids.getIDs()) {
                    TwitterController.getTwitterController().followakGorde(Long.toString(id), twitter.showUser(id).getScreenName(), twitter.getScreenName());
                    
                }
            } while (((cursor = ids.getNextCursor()) != 0) && (ids.getIDs().length!=0));
        } catch (TwitterException te) {
        	 JDialog info =new InformazioMezua("application's rate limit, please wait 15m a retry");
 			info.setVisible(true);
        }
	}
	
	/**
	 * Listak jaitsi eta gordetzen ditu
	 */
	public void getLists(){
		try {
            ResponseList<UserList> lists = twitter.getUserLists(twitter.getScreenName());
            for (UserList list : lists) {

                //get members of a list
                long cursor = -1;
                PagableResponseList<twitter4j.User> usres;
                do {
                    usres = twitter.getUserListMembers(list.getId(), cursor);
                    for (twitter4j.User lista : usres) {
                    	TwitterController.getTwitterController().listakGorde(list.getId(),list.getName(),lista.getScreenName(),twitter.getScreenName());
                    }
                } while (((cursor = usres.getNextCursor()) != 0) && (!usres.isEmpty()));
                
            }
            
        } catch (TwitterException te) {
        	 JDialog info =new InformazioMezua("application's rate limit, please wait 15m a retry");
 			info.setVisible(true);
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
                	TwitterController.getTwitterController().mezuaGorde(Long.toString(message.getId()), message.getSenderScreenName(), twitter.getScreenName(), message.getText(), twitter.getScreenName());
                }
                page.setPage(page.getPage() + 1);
            } while (messages.size() > 0 /*&& paging.getPage() < 10*/);
            
            //nik bidalitakoak
            page = new Paging(1);
            do {
                messages = twitter.getSentDirectMessages(page);
                for (DirectMessage message : messages) {
                	TwitterController.getTwitterController().mezuaGorde(Long.toString(message.getId()), twitter.getScreenName(), message.getRecipientScreenName(), message.getText(), twitter.getScreenName());
                }
                page.setPage(page.getPage() + 1);
            } while (messages.size() > 0 /*&& page.getPage() < 10*/);
            
        } catch (TwitterException te) {
        	 JDialog info =new InformazioMezua("application's rate limit, please wait 15m a retry");
 			info.setVisible(true);
        }
	}
	
}



