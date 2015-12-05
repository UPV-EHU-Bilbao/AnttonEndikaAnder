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
import java.util.Iterator;
import java.util.LinkedList;
import controller.TwitterController;



public class HasierakoMenua extends JFrame implements ActionListener{

	private JPanel contentPane;
	private int altuera=500;
	private int zabalera=650;
	private static HasierakoMenua frame = new HasierakoMenua();
	private JButton gehiago=new JButton("20 twit gehiago");
	private final JMenuBar menuBar = new JMenuBar();
	private JMenu menuUser;
	private String twitterUser;
	private TweetPanela tweetTaula=new TweetPanela();
	/**
	 * Launch the application.
	 */
	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//
	//					frame.setVisible(true);
	//					frame.setMinimumSize(new Dimension(450, 300));
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	/**
	 * Create the frame.
	 */
	public HasierakoMenua() {


		setTitle("Hasierako menua");

		twitterUser=null;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, zabalera, altuera);
		setMinimumSize(new Dimension(450, 300));

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
		JMenuItem mntmFav = new JMenuItem("fav");
		JMenuItem mntmRetweet = new JMenuItem("Retweet");
		mntmRetweet.setMinimumSize(new Dimension(40, 20));
		JMenuItem mntmDM = new JMenuItem("Direct message");
		mntmDM.setMinimumSize(new Dimension(120, 20));
		JMenuItem mntmDeskargatu = new JMenuItem("Deskargatu");
		mntmDeskargatu.setMinimumSize(new Dimension(80, 20));
		mntmDeskargatu.addActionListener(this);
		mntmDeskargatu.setActionCommand("deskargatu");
		menuBar.add(mntmTweet);
		menuBar.add(mntmFav);
		menuBar.add(mntmRetweet);
		menuBar.add(mntmDM);
		menuBar.add(mntmDeskargatu);

		menuUser =new JMenu("Users");
		menuUser.setMinimumSize(new Dimension(50, 20));
		menuBar.add(menuUser);
		
		//users botioaren inplementazioa
		newUserMenua();

		//tweet-en taula
		tweetTaula.setOpaque(true);
		contentPane.add(tweetTaula, BorderLayout.CENTER);
		
		
	} 
	
	public void newUserMenua() {
		LinkedList<String> lk=User.getUser().getTwitterUsers();
		Iterator<String> it=lk.iterator();
		JMenuItem item=null;
		String str= it.next();
		item =new JMenuItem("@"+str);
		menuUser.add(item);
		item.addActionListener(this);
		item.setActionCommand(str);
		twitterUser=str;
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
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals("adduser")){
			TwitterConect tc=new TwitterConect();
			tc.login();
			menuUser.removeAll();
			newUserMenua();

		}
		else if (arg0.getActionCommand().equals("tweet") || arg0.getActionCommand().equals("20+")){
			//bukaerara heltzean errorea
			ArrayList<String> st=new ArrayList<String>();
			//MyTableModelTweet tableModel =new MyTableModelTweet(st);
			
			try {
				
				if(arg0.getActionCommand().equals("20+")){
					st = TwitterController.getTwitterController().tweetakIkusi(this.twitterUser);
				}else{
					st=TwitterController.getTwitterController().lehentweetakIkusi(this.twitterUser);
					gehiago.setEnabled(true);
				}
				tweetTaula.gehiago20(st);
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(arg0.getActionCommand().equals("deskargatu")){
			Deskargak dialog = new Deskargak();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}else{
			twitterUser=arg0.getActionCommand();
		}
	}


}
