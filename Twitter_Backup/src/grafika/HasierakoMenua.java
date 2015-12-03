package grafika;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.management.Query;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;

//import com.mysql.fabric.xmlrpc.base.Data;














import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.JScrollBar;

import model.TwitterConect;
import model.User;

import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.omg.CORBA.TCKind;

import com.mysql.jdbc.RowData;

import controller.DB;
import controller.TwitterController;

public class HasierakoMenua extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JScrollPane scrollPane = new JScrollPane();
	private int altuera=300;
	private int zabalera=450;
	private static HasierakoMenua frame = new HasierakoMenua();
	private JTextArea textArea = new JTextArea();
	private JButton gehiago=new JButton("20 twit gehiago");
	private final JMenuBar menuBar = new JMenuBar();
	private JMenu menuUser;
	private String twitterUser;

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

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, zabalera, altuera);
		setMinimumSize(new Dimension(450, 300));

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		contentPane.add(scrollPane,BorderLayout.CENTER);


		contentPane.add(gehiago, BorderLayout.SOUTH);
		gehiago.setVisible(false);
		gehiago.addActionListener(this);
		gehiago.setActionCommand("20+");


		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		textArea.setColumns(20);
		textArea.setWrapStyleWord(true);

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
		LinkedList<String> lk=User.getUser().getTwitterUsers();
		Iterator<String> it=lk.iterator();
		JMenuItem item=null;
		while(it.hasNext()){
			String str= it.next();
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
			System.out.println("add user");
			TwitterConect tc=new TwitterConect();
			tc.login();
			System.out.println("sartu da");
			menuUser.removeAll();
			LinkedList<String> lk=User.getUser().getTwitterUsers();
			Iterator<String> it=lk.iterator();
			JMenuItem item=null;
			while(it.hasNext()){
				String str= it.next();
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
		else if (arg0.getActionCommand().equals("tweet") || arg0.getActionCommand().equals("20+")){
			//bukaerara heltzean errorea
			LinkedList<String> st=new LinkedList<String>();
			try {
				String mesage=new String();
				if(arg0.getActionCommand().equals("20+")){
					st = TwitterController.getTwitterController().tweetakIkusi(this.twitterUser);
					mesage=textArea.getText();
					if(!st.isEmpty()){
						mesage =mesage+"\n"+"\n-*"+st.removeFirst();
					}
				}else{
					st=TwitterController.getTwitterController().lehentweetakIkusi(this.twitterUser);
					if(!st.isEmpty()){
						mesage ="-*"+st.removeFirst();
					}
				}
				int color=0;
				while(!st.isEmpty()){
					String ms=st.removeFirst();
					if(!ms.equals(null)){
						mesage=mesage+" "+"\n"+"\n-*"+ms;}
				}
				textArea.setText(mesage);
				gehiago.setVisible(true);
				scrollPane.getVerticalScrollBar().setValue(0);
				Point p = new Point(1,1);
				scrollPane.getViewport().setViewPosition(p); 

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
