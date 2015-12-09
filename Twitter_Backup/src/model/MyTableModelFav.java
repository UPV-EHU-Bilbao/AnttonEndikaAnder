package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import model.MyTableModelTweet.Lag;

public class MyTableModelFav extends AbstractTableModel {

	private ArrayList<String> columnNames;

	public MyTableModelFav() {

		columnNames=new ArrayList<String>();
		hasieratuZutabeIzenak();
		//kargatu(st);
//		while(!st.isEmpty()){
//			
//		data.add(new Lag(st.removeFirst()));
		//System.out.println(data.get(kont++).tweet);
//		}

	}
	
	public void hasieratuZutabeIzenak() {
		columnNames.add("Twitter User");
		columnNames.add("Fav");
		
	}

	class Lag {
		String twitterUser;
		String fav;
		public Lag(String pTwitterUser, String pFav) {
			super();
			this.twitterUser = pTwitterUser;
			this.fav=pFav;
		}
		public Object getBalioa(int i) {
			if(i==0){
				return twitterUser;
			}else if(i==1){
				return fav;
			}else {
				return null;
			}
		}

		public void insertElementAt(Object value, int i){
			if(i==0){
				twitterUser=(String) value;
			}else if(i==1){
				fav=(String) value;
			}
		}

	}

	private Vector<Lag> data= new Vector<Lag>();

	public void kargatu(List<String[]> st) {
		for (String[] elementua : st){
			data.add(new Lag(elementua[1], elementua[0]));
		}
		;
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
