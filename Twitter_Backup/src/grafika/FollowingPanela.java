package grafika;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import model.MyTableModelFollowing;

public class FollowingPanela extends JPanel{

	private MyTableModelFollowing modeloa;
	private JTable table;
	private JScrollPane skrolla;

	
	public FollowingPanela() {
		super(new GridLayout(1, 0));
		modeloa=new MyTableModelFollowing();
		table =new JTable(modeloa);
		skrolla = new JScrollPane(table);
		this.add(skrolla);
		
	}
	
	
	public void gehiago20(List<String> st) {
		
		modeloa.kargatu(st);
		table.revalidate();
		this.revalidate();
		
	}
	
	public void ezabatuFollowing() {
		modeloa.ezabatu();
		
	}
	
	
}


