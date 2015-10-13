package grafika;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;

import com.mysql.fabric.xmlrpc.base.Data;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JScrollBar;

import model.Dd;

import java.awt.GridLayout;
import java.sql.SQLException;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HasierakoMenua extends JFrame implements ActionListener{

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HasierakoMenua frame = new HasierakoMenua();
					frame.setVisible(true);
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JMenuItem mntmTweet = new JMenuItem("tweet");
		mntmTweet.setBounds(12, 0, 77, 19);
		contentPane.add(mntmTweet);
		mntmTweet.addActionListener(this);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 44, 383, 193);
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		//textArea.
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Dd data =new Dd();
		try {
			data.tweetakIkusi();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
