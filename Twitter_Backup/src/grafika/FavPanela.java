package grafika;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import model.MyTableModelFav;

public class FavPanela extends JPanel{
	
	private MyTableModelFav modeloa;
	private JTable table;
	private JScrollPane skrolla;

	
	public FavPanela() {
		super(new GridLayout(1, 0));
		modeloa=new MyTableModelFav();
		table =new JTable(modeloa);
		skrolla = new JScrollPane(table);
		this.add(skrolla);
		
	}
	
	
	public void gehiago20(List<String[]> st) {
		
		modeloa.kargatu(st);
		table.revalidate();
		this.revalidate();
		
	}
	
	public void ezabatuFav() {
		modeloa.ezbatu();
	}
	
	
}
