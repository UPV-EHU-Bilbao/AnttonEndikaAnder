package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import model.MyTableModelFav.Lag;

public class MyTableModelMezuak extends AbstractTableModel {

	private ArrayList<String> columnNames;

	public MyTableModelMezuak() {

		columnNames=new ArrayList<String>();
		hasieratuZutabeIzenak();

	}
	
	public void hasieratuZutabeIzenak() {
		columnNames.add("From");
		columnNames.add("To");
		columnNames.add("Direct Mesage");
		
	}

	class Lag {
		String from;
		String directMesage;
		String to;
		public Lag(String pFrom,String pTo, String pDirectMesage) {
			super();
			this.from = pFrom;
			this.directMesage=pDirectMesage;
			this.to=pTo;
		}
		public Object getBalioa(int i) {
			if(i==0){
				return from;
			}else if(i==1){
				return to;
			}else if(i==2){
				return directMesage;
			}else{
				return null;
			}
		}

		public void insertElementAt(Object value, int i){
			if(i==0){
				from=(String) value;
			}else if(i==1){
				to=(String) value;
			}else if(i==2){
				directMesage=(String) value;
			}
		}

	}

	private Vector<Lag> data= new Vector<Lag>();

	public void kargatu(List<String[]> st) {
		for (String[] elementua : st){
			data.add(new Lag(elementua[0], elementua[1], elementua[2]));
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