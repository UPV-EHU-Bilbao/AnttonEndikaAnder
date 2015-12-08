package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Export {
	
	public void exportToExcel(ArrayList<ArrayList<Object>> tweets){

		HSSFWorkbook workbook = new HSSFWorkbook();
		
		//create a new page tweets
		HSSFSheet tweetPage = workbook.createSheet("tweets");
		
		ArrayList<ArrayList<Object>> tweetsWithTitle = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> title = new ArrayList<Object>();
		title.add("id");
		title.add("status");
		tweetsWithTitle.addAll(tweets);
		int rownum = 0;
		for (ArrayList<Object> tweet : tweetsWithTitle) {
		    Row row = tweetPage.createRow(rownum++);
		    int cellnum = 0;
		    for (Object obj : tweet) {
		        Cell cell = row.createCell(cellnum++);
		        if(obj instanceof Date) 
		            cell.setCellValue((Date)obj);
		        else if(obj instanceof Boolean)
		            cell.setCellValue((Boolean)obj);
		        else if(obj instanceof String)
		            cell.setCellValue((String)obj);
		        else if(obj instanceof Double)
		            cell.setCellValue((Double)obj);
		        else if(obj instanceof Integer)
		            cell.setCellValue((Integer)obj);
		    }
		}
		 
		try {
		    FileOutputStream out = new FileOutputStream(new File("TwitterBackup.xls"));
		    workbook.write(out);
		    out.close();
		    System.out.println("Excel written successfully..");
		     
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}
