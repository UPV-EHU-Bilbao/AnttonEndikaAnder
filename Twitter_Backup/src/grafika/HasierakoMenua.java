package grafika;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JMenu; 
import javax.swing.JMenuItem;
import java.awt.Dimension;
import model.TwitterConect;
import model.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import controller.TwitterController;



public class HasierakoMenua extends JFrame implements ActionListener{

	private JPanel contentPane;
	private int altuera=500;
	private int zabalera=650;
	private JButton gehiago=new JButton("20 Gehiago");
	private final JMenuBar menuBar = new JMenuBar();
	private JMenu menuUser;
	private String twitterUser;
	private TweetPanela tweetTaula=new TweetPanela();
	private FavPanela favPanela =new FavPanela();
	private FollowerPanela followerPanela= new FollowerPanela();
	private FollowingPanela followingPanela=new FollowingPanela();
	private MezuakPanela mezuakPanela=new MezuakPanela();
	private ListakPanela listakPanela=new ListakPanela();
	private boolean panelaDago=false;
	

	/**
	 * Create the frame.
	 */
	public HasierakoMenua() {


		setTitle("Hasierako menua");

		twitterUser=null;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, zabalera, altuera);
		setMinimumSize(new Dimension(700, 300));

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());


		contentPane.add(gehiago, BorderLayout.SOUTH);
		gehiago.setEnabled(false);
		gehiago.addActionListener(this);
		gehiago.setActionCommand("20+");


		JPanel contentPane1 = new JPanel();
		contentPane.add(contentPane1, BorderLayout.NORTH);
		contentPane1.setLayout(new BorderLayout(0, 0));
		contentPane1.add(menuBar);

		JMenuItem mntmTweet = new JMenuItem("Tweet");
		mntmTweet.addActionListener(this);
		mntmTweet.setActionCommand("tweet");
		mntmTweet.setMinimumSize(new Dimension(35, 20));
		
		JMenuItem mntmFav = new JMenuItem("Fav");
		mntmFav.addActionListener(this);
		mntmFav.setActionCommand("fav");
		mntmFav.setMinimumSize(new Dimension(10, 20));
		
		JMenuItem mntmFollowers = new JMenuItem("Followers");
		mntmFollowers.setMinimumSize(new Dimension(60, 20));
		mntmFollowers.addActionListener(this);
		mntmFollowers.setActionCommand("followers");
		
		JMenuItem mntmFollowing = new JMenuItem("Following");
		mntmFollowing.setMinimumSize(new Dimension(60, 20));
		mntmFollowing.addActionListener(this);
		mntmFollowing.setActionCommand("following");
		
		JMenuItem mntmDM = new JMenuItem("Direct message");
		mntmDM.setMinimumSize(new Dimension(120, 20));
		mntmDM.addActionListener(this);
		mntmDM.setActionCommand("DM");
		
		JMenuItem mntmList = new JMenuItem("List");
		mntmList.addActionListener(this);
		mntmList.setActionCommand("list");
		mntmList.setMinimumSize(new Dimension(10, 20));
		
		JMenuItem mntmDeskargatu = new JMenuItem("Deskargatu");
		mntmDeskargatu.setMinimumSize(new Dimension(80, 20));
		mntmDeskargatu.addActionListener(this);
		mntmDeskargatu.setActionCommand("deskargatu");
		
		JMenuItem mntmExcel = new JMenuItem("Export to Excel");
		mntmExcel.setMinimumSize(new Dimension(120, 20));
		
		menuBar.add(mntmTweet);
		menuBar.add(mntmFav);
		menuBar.add(mntmFollowers);
		menuBar.add(mntmFollowing);
		menuBar.add(mntmDM);
		menuBar.add(mntmList);
		menuBar.add(mntmDeskargatu);
		menuBar.add(mntmExcel);


		menuUser =new JMenu("Users");
		menuUser.setMinimumSize(new Dimension(50, 20));
		menuBar.add(menuUser);

		//users botioaren inplementazioa
		newUserMenua();

		


	} 

	public void newUserMenua() {
		ArrayList<String> lk=User.getUser().getTwitterUsers();
		Iterator<String> it=lk.iterator();
		JMenuItem item=null;
		String str=null;
		if (!lk.isEmpty()){
			str= it.next();
			item =new JMenuItem("@"+str);
			menuUser.add(item);
			item.addActionListener(this);
			item.setActionCommand(str);
			twitterUser=str;
			setTitle("Hasierako menua: "+str);
		}
		while(it.hasNext()){
			str= it.next();
			item =new JMenuItem("@"+str);
			menuUser.add(item);
			item.addActionListener(this);
			item.setActionCommand(str);
		}
		item=new JMenuItem("Add Twitter User");
		menuUser.add(item);
		item.addActionListener(this);
		item.setActionCommand("adduser");
	}



	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("adduser")){
			TwitterConect tc=new TwitterConect();
			tc.login();
			menuUser.removeAll();
			newUserMenua();

		}
		else if (event.getActionCommand().equals("tweet") || event.getActionCommand().equals("20+tweet")){
			//bukaerara heltzean errorea
			ArrayList<String> st=new ArrayList<String>();
			try {
				if(panelaDago){
				contentPane.remove(2);
				}
				//tweet-en taula
				tweetTaula.setOpaque(true);
				contentPane.add(tweetTaula, BorderLayout.CENTER);
				panelaDago=true;
				
				if(event.getActionCommand().equals("20+tweet")){
					
					st = TwitterController.getTwitterController().tweetakIkusi(this.twitterUser, false);
				}else{
					tweetTaula.ezabatuTweetak();
					st=TwitterController.getTwitterController().tweetakIkusi(this.twitterUser, true);
					gehiago.setEnabled(true);
					gehiago.setActionCommand("20+tweet");
				}
				tweetTaula.gehiago20(st);


			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if (event.getActionCommand().equals("fav") || event.getActionCommand().equals("20+fav")){
			try {
				if(panelaDago){
					contentPane.remove(2);
				}
				//fav taula
				favPanela.setOpaque(true);
				contentPane.add(favPanela, BorderLayout.CENTER);
				ArrayList<String[]> favLista=new ArrayList<String[]>();
				panelaDago=true;
				
				if(event.getActionCommand().equals("20+fav")){
					favLista = TwitterController.getTwitterController().favIkusi(this.twitterUser, false);
				}else{
					favPanela.ezabatuFav();
					favLista=TwitterController.getTwitterController().favIkusi(this.twitterUser, true);
					gehiago.setEnabled(true);
					gehiago.setActionCommand("20+fav");
				}
				favPanela.gehiago20(favLista);


			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if(event.getActionCommand().equals("followers") || event.getActionCommand().equals("20+follow")){
			if(panelaDago){
				contentPane.remove(2);
			}
			//follower taula
			followerPanela.setOpaque(true);
			contentPane.add(followerPanela, BorderLayout.CENTER);
			ArrayList<String> followerLista=new ArrayList<String>();
			panelaDago=true;
			
			if(event.getActionCommand().equals("20+follow")){
				followerLista = TwitterController.getTwitterController().followerakIkusi(this.twitterUser, false);
			}else{ 
				followerPanela.ezabatuFollowerrak();
				followerLista=TwitterController.getTwitterController().followerakIkusi(this.twitterUser, true);
				gehiago.setEnabled(true);
				gehiago.setActionCommand("20+follow");
			}
			followerPanela.gehiago20(followerLista);
			
		}else if(event.getActionCommand().equals("following") || event.getActionCommand().equals("20+following")){
			if(panelaDago){
				contentPane.remove(2);
			}
			//following taula
			followingPanela.setOpaque(true);
			contentPane.add(followingPanela, BorderLayout.CENTER);
			ArrayList<String> followingLista=new ArrayList<String>();
			panelaDago=true;
			
			if(event.getActionCommand().equals("20+following")){
				followingLista = TwitterController.getTwitterController().followakIkusi(this.twitterUser, false);
			}else{ 
				followingPanela.ezabatuFollowing();
				followingLista=TwitterController.getTwitterController().followakIkusi(this.twitterUser, true);
				gehiago.setEnabled(true);
				gehiago.setActionCommand("20+following");
			}
			followingPanela.gehiago20(followingLista);
			
		}else if (event.getActionCommand().equals("DM") || event.getActionCommand().equals("20+dm")){
			if(panelaDago){
				contentPane.remove(2);
			}
			//DM taula
			mezuakPanela.setOpaque(true);
			contentPane.add(mezuakPanela, BorderLayout.CENTER);
			ArrayList<String[]> dmLista=new ArrayList<String[]>();
			panelaDago=true;
			
			if(event.getActionCommand().equals("20+dm")){
				dmLista = TwitterController.getTwitterController().mezuakIkusi(this.twitterUser, false);
			}else{
				mezuakPanela.ezabatuFav();
				dmLista=TwitterController.getTwitterController().mezuakIkusi(this.twitterUser, true);
				gehiago.setEnabled(true);
				gehiago.setActionCommand("20+dm");
			}
			mezuakPanela.gehiago20(dmLista);
		}else if (event.getActionCommand().equals("list") || event.getActionCommand().equals("20+list")){
			if(panelaDago){
				contentPane.remove(2);
			}
			//lista taula
			listakPanela.setOpaque(true);
			contentPane.add(listakPanela, BorderLayout.CENTER);
			panelaDago=true;
			HashMap<String, ArrayList<String>> listLista = TwitterController.getTwitterController().listakIkusi(twitterUser);
			listakPanela.gehiago20(listLista);
		}else if(event.getActionCommand().equals("deskargatu")){
			Deskargak dialog = new Deskargak(this.twitterUser);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}else{
			twitterUser=event.getActionCommand();
			setTitle("Hasierako menua: "+event.getActionCommand());
		}
	}


}
