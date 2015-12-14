package grafika;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import model.MyTableModelList;

public class ListakPanela extends JPanel {

	private MyTableModelList modeloa;
	private JTable table;
	private JScrollPane skrolla;

	public ListakPanela() {
		super(new GridLayout(1, 0));
		modeloa = new MyTableModelList();
		table = new JTable(modeloa);
		
		skrolla = new JScrollPane(table);
		this.add(skrolla);

	}

	private void doituZutabeZabalera() {
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		for (int column = 0; column < table.getColumnCount(); column++) {
			TableColumn tableColumn = table.getColumnModel().getColumn(column);
			int preferredWidth = tableColumn.getMinWidth();
			int maxWidth = tableColumn.getMaxWidth();

			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer cellRenderer = table.getCellRenderer(row,
						column);
				Component c = table.prepareRenderer(cellRenderer, row, column);
				int width = c.getPreferredSize().width
						+ table.getIntercellSpacing().width;
				preferredWidth = Math.max(preferredWidth, width);

				// We've exceeded the maximum width, no need to check other rows

				if (preferredWidth >= maxWidth) {
					preferredWidth = maxWidth;
					break;
				}
			}

			tableColumn.setPreferredWidth(preferredWidth);
		}
	}

	public void gehiago20(HashMap<String, ArrayList<String>> st) {

		modeloa.kargatu(st);
		if(!st.isEmpty()){
			doituZutabeZabalera();
		}
		table.revalidate();
		this.revalidate();

	}

	public void ezabatuLista() {
		modeloa.ezbatu();
	}

}