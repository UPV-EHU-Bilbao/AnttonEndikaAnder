package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class MyTableModelList extends AbstractTableModel {

	private ArrayList<String> columnNames;

	public MyTableModelList() {

		columnNames=new ArrayList<String>();
		hasieratuZutabeIzenak();
	}
	
	public void hasieratuZutabeIzenak() {
		columnNames.add("List Name");
		columnNames.add("List Menbers");
		
	}

	class Lag {
		String listName;
		String listMenbers;
		public Lag(String pListname, String pListMenbers) {
			super();
			this.listName = pListname;
			this.listMenbers=pListMenbers;
		}
		public Object getBalioa(int i) {
			if(i==0){
				return listName;
			}else if(i==1){
				return listMenbers;
			}else {
				return null;
			}
		}

		public void insertElementAt(Object value, int i){
			if(i==0){
				listName=(String) value;
			}else if(i==1){
				listMenbers=(String) value;
			}
		}

	}

	private Vector<Lag> data= new Vector<Lag>();

	public void kargatu(HashMap<String, ArrayList<String>> st) {
		
		for (String menberList : st.keySet()){
			for(String menber: st.get(menberList))
			data.add(new Lag(menberList, menber));
		}
	}
	
	public void ezbatu() {
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
