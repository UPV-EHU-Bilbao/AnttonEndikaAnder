
package grafika;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.MyTableModelTweet;

public class TweetPanela extends JPanel{

	private MyTableModelTweet modeloa;
	private JTable table;
	private JScrollPane skrolla;

	
	public TweetPanela() {
		super(new GridLayout(1, 0));
		// lista.addAll(st);
		modeloa=new MyTableModelTweet();
		//myTable.kargatu(st);
		table =new JTable(modeloa);
		skrolla = new JScrollPane(table);
		this.add(skrolla);
		
	}
	
//	public static void createAndShowGUI(LinkedList<String> st) {
//		JFrame frame=new JFrame("Table Demo");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		TweetTaula contentpane =new TweetTaula();
//		contentpane.setOpaque(true);
//		frame.setContentPane(contentpane);
//		frame.pack();
//		frame.setVisible(true);
//	}
	
	public void gehiago20(List<String> st) {
		
		modeloa.kargatu(st);
		// this.removeAll();
		// JTable table1 =new JTable(myTable);
		// this.add(table1);
		table.revalidate();
		this.revalidate();
		
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


