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

import javax.swing.JScrollBar;

import model.Dd;
import model.TwitterConect;

import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.Stack;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HasierakoMenua extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JScrollPane scrollPane = new JScrollPane();
	private int altuera=300;
	private int zabalera=450;
	private static HasierakoMenua frame = new HasierakoMenua();
	private JTextArea textArea = new JTextArea();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					frame.setVisible(true);
					frame.setMinimumSize(new Dimension(450, 300));
					System.out.println((frame.getWidth())+" "+(frame.getHeight()));
//					 WindowEvent we = new WindowEvent(frame, WindowEvent.COMPONENT_RESIZED);
//					 WindowListener wl = null;
//					    wl.windowActivated(we);
//					    frame.addWindowListener(wl);
					//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		
		JMenuItem mntmTweet = new JMenuItem("tweet");
		mntmTweet.setBounds(12, 0, 77, 19);
		contentPane.add(mntmTweet);
		mntmTweet.addActionListener(this);
		
		scrollPane.setBounds(26, 44, zabalera-70, altuera-100);
		contentPane.add(scrollPane);
		//scrollPane.add
		
		
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
	} 

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Dd data =new Dd();
		//TwitterConect tc =new TwitterConect();
		//tc.Login();
		try {
			Stack<String> st=data.tweetakIkusi();
			String mesage =null;
			while(!st.isEmpty()){
				String ms=st.pop();
				if(!ms.equals(null)){
				mesage=ms+" "+"\n"+mesage;}
			}
			textArea.setText(mesage);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void windowIconified(WindowEvent e){
		scrollPane.setBounds(100, 100, frame.getWidth()-70, frame.getHeight()-100);
		System.out.println((contentPane.getWidth()-70)+" "+(contentPane.getHeight()-100));
	}

	
}
