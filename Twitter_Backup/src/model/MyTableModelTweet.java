package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class MyTableModelTweet extends AbstractTableModel {

	private ArrayList<String> columnNames;

	public MyTableModelTweet(LinkedList<String> st) {

		columnNames=new ArrayList<String>();
		hasieratuZutabeIzenak();
		//kargatu(st);
		while(!st.isEmpty()){
		data.add(new Lag(st.removeFirst()));
		}

	}
	
	public void hasieratuZutabeIzenak() {
		columnNames.add("Tweet");
		
	}

	class Lag {
		String tweet;
		public Lag(String tweet) {
			super();
			this.tweet = tweet;
		}
		public Object getBalioa(int i) {
			if(i==0){
				return tweet;
			}else {
				return null;
			}
		}

		public void insertElementAt(Object value, int i){
			if(i==0){
				tweet=(String) value;
			}
		}

	}

	private Vector<Lag> data= new Vector<Lag>();

	public void kargatu(String st) {
//		data.add(new Lag("kathy", "smith", "snow", 5, false));
			data.add(new Lag(st));		
			
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
