package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

public class MyTableModelFollowing extends AbstractTableModel {

	private ArrayList<String> columnNames;

	public MyTableModelFollowing() {

		columnNames=new ArrayList<String>();
		hasieratuZutabeIzenak();

	}
	
	public void hasieratuZutabeIzenak() {
		columnNames.add("Following");
		
	}

	class Lag {
		String following;
		public Lag(String pFollowing) {
			super();
			this.following = pFollowing;
		}
		public Object getBalioa(int i) {
			if(i==0){
				return following;
			}else {
				return null;
			}
		}

		public void insertElementAt(Object value, int i){
			if(i==0){
				following=(String) value;
			}
		}

	}

	private Vector<Lag> data= new Vector<Lag>();

	public void kargatu(List<String> st) {
		for (String elementua : st){
			data.add(new Lag(elementua));
		}
		;
	}
	
	public void ezabatu() {
		data.removeAllElements();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.size();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		return data.elementAt(row).getBalioa(col);
	}

	public String getColumnName(int i) {
		return columnNames.get(i);
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	public Class getColumnClass(int c) {
		return data.elementAt(0).getBalioa(c).getClass(); 
	}
	
	public void setValueAt(Object value, int row, int col) {
		data.elementAt(row).insertElementAt(value,col);
	}


}
