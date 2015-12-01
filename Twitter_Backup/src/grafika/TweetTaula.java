
package grafika;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.MyTableModelTweet;

public class TweetTaula extends JPanel{

	private LinkedList<String> lista;
	private MyTableModelTweet myTable;
	
	public TweetTaula(LinkedList<String> st) {
		super(new GridLayout(1, 0));
		lista=new LinkedList<String>();
		for (String tweet : st) {
			lista.add(tweet);
		}
		myTable=new MyTableModelTweet();
		myTable.kargatu(st);
		JTable table =new JTable(myTable);
		JScrollPane scrollpane = new JScrollPane(table);
		this.add(scrollpane);
	}
	
	public static void createAndShowGUI(LinkedList<String> st) {
		JFrame frame=new JFrame("Table Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		TweetTaula contentpane =new TweetTaula(st);
		contentpane.setOpaque(true);
		frame.setContentPane(contentpane);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void gehiago20(LinkedList<String> st) {
		myTable.kargatu(st);
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				//createAndShowGUI();
				
			}
		});
	}
	
}


