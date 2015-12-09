
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
		modeloa=new MyTableModelTweet();
		table =new JTable(modeloa);
		skrolla = new JScrollPane(table);
		this.add(skrolla);
		
	}
	
	
	public void gehiago20(List<String> st) {
		
		modeloa.kargatu(st);
		table.revalidate();
		this.revalidate();
		
	}
	
	public void ezabatuTweetak() {
		modeloa.ezabatu();
		
	}
	
	
}


