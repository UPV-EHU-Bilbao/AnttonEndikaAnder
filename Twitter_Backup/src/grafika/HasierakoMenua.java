package grafika;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
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

import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.Stack;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.Dd;

public class HasierakoMenua extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JScrollPane scrollPane = new JScrollPane();
	private int altuera=300;
	private int zabalera=450;
	private static HasierakoMenua frame = new HasierakoMenua();
	private JTextArea textArea = new JTextArea();
	private JButton gehiago=new JButton("20 twit gehiago");
	private final JMenuBar menuBar = new JMenuBar();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					frame.setVisible(true);
					frame.setMinimumSize(new Dimension(450, 300));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HasierakoMenua() {
		
		//int altuera=300;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, zabalera, altuera);
		
		//setJMenuBar(menuBar);
		//contentPane.add(menuBar, BorderLayout.NORTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		contentPane.add(scrollPane,BorderLayout.CENTER);
		//menuUser.add(new JMenuItem("userproba"));
		
		
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
		
		JMenuItem mntmTweet = new JMenuItem("tweet");
		//contentPane.add(mntmTweet, BorderLayout.NORTH);
		mntmTweet.addActionListener(this);
		//scrollPane.add
		JMenuItem mntmFav = new JMenuItem("fav");
		JMenuItem mntmRetweet = new JMenuItem("Retweet");
		JMenuItem mntmDM = new JMenuItem("Direct message");
		mntmDM.setMinimumSize(new Dimension(120, 20));
		menuBar.add(mntmTweet);
		menuBar.add(mntmFav);
		menuBar.add(mntmRetweet);
		menuBar.add(mntmDM);
		
		JMenu menuUser =new JMenu("users");
		menuUser.setMinimumSize(new Dimension(50, 20));
		//contentPane.add(menuUser, BorderLayout.NORTH);
		//menuUser.add(new JMenuItem("user1"));
		menuBar.add(menuUser);
		//scrollPane.getVerticalScrollBar().setValue(0);
	} 

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Dd data =new Dd();
//		TwitterConect tc =new TwitterConect();
//		tc.login();
//		tc.getTwitts();
		try {
			Long zenb=new Long(data.getAzkenId());
			Stack<String> st=data.tweetakIkusi(zenb,zenb+2000000000);
			String mesage=new String();
			if(arg0.getActionCommand().equals("20+")){
				mesage=textArea.getText();
				//System.out.println(mesage);
				mesage =mesage+"\n"+"\n-*"+st.pop();
				
				
			}else{
				mesage ="-"+st.pop();
			}
			int color=0;
			while(!st.isEmpty()){
				String ms=st.pop();
				//System.out.println(ms);
				if(!ms.equals(null)){
 				 mesage=mesage+ms+" "+"\n"+"\n-*";}
//				if(color%2==0){
//					textArea.setForeground(Color.BLUE);
//					textArea.setText(mesage);
//				}else{
//					textArea.setForeground(Color.RED);
//					textArea.setText(mesage);
//				}
//				color++;
			}
			textArea.setText(mesage);
			
			gehiago.setVisible(true);
//			scrollPane.setCorner(scrollPane.UPPER_LEFT_CORNER, null );
			scrollPane.getVerticalScrollBar().setValue(0);
			///scrollPane.setC
			//scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
			Point p = new Point(1,1);
			scrollPane.getViewport().setViewPosition(p); 
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
