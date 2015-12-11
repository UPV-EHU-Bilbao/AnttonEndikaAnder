package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JFileChooser;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Export {
	
	public void exportToExcel(String user, boolean getTweet, boolean getFav, boolean getFollows, boolean getFollowers, boolean getDirectMesage, boolean getLists){

		HSSFWorkbook workbook = new HSSFWorkbook();
		
		if (getTweet){
		//create a new page tweets
			HSSFSheet tweetPage = workbook.createSheet("tweets");
			
			ArrayList<ArrayList<String>> tweetsWithTitle = new ArrayList<ArrayList<String>>();
			ArrayList<String> title = new ArrayList<String>();
			title.add("id");
			title.add("status");
			tweetsWithTitle.add(title);
			ArrayList<ArrayList<String>> tweets = ExportController.getExportController().getTweets(user);;
			tweetsWithTitle.addAll(tweets);
			int rownum = 0;
			for (ArrayList<String> tweet : tweetsWithTitle) {
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
		}
		
		if (getFav){
			//create a new page favs
				HSSFSheet favsPage = workbook.createSheet("favs");
				
				ArrayList<ArrayList<String>> tweetsWithTitle = new ArrayList<ArrayList<String>>();
				ArrayList<String> title = new ArrayList<String>();
				title.add("id");
				title.add("name");
				title.add("status");
				tweetsWithTitle.add(title);
				ArrayList<ArrayList<String>> tweets = ExportController.getExportController().getFavs(user);
				tweetsWithTitle.addAll(tweets);
				int rownum = 0;
				for (ArrayList<String> tweet : tweetsWithTitle) {
				    Row row = favsPage.createRow(rownum++);
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
			}
		if (getFollows){
			//create a new page follows
				HSSFSheet followsPage = workbook.createSheet("follows");
				
				ArrayList<ArrayList<String>> followsWithTitle = new ArrayList<ArrayList<String>>();
				ArrayList<String> title = new ArrayList<String>();
				title.add("id");
				title.add("name");
				followsWithTitle.add(title);
				ArrayList<ArrayList<String>> follows = ExportController.getExportController().getFollows(user);
				followsWithTitle.addAll(follows);
				int rownum = 0;
				for (ArrayList<String> follow : followsWithTitle) {
				    Row row = followsPage.createRow(rownum++);
				    int cellnum = 0;
				    for (Object obj : follow) {
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
			}
		
		if (getFollowers){
			//create a new page follows
				HSSFSheet followersPage = workbook.createSheet("followers");
				
				ArrayList<ArrayList<String>> followersWithTitle = new ArrayList<ArrayList<String>>();
				ArrayList<String> title = new ArrayList<String>();
				title.add("id");
				title.add("name");
				followersWithTitle.add(title);
				ArrayList<ArrayList<String>> followers = ExportController.getExportController().getFollowers(user);
				followersWithTitle.addAll(followers);
				int rownum = 0;
				for (ArrayList<String> follower : followersWithTitle) {
				    Row row = followersPage.createRow(rownum++);
				    int cellnum = 0;
				    for (Object obj : follower) {
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
			}
		
		if (getDirectMesage){
			//create a new page mesage
			HSSFSheet mesagesPage = workbook.createSheet("DirectMesages");
			
			ArrayList<ArrayList<String>> mesagesWithTitle = new ArrayList<ArrayList<String>>();
			ArrayList<String> title = new ArrayList<String>();
			title.add("id");
			title.add("from");
			title.add("to");
			title.add("mesage");
			mesagesWithTitle.add(title);
			ArrayList<ArrayList<String>> mesages = ExportController.getExportController().getMesages(user);
			mesagesWithTitle.addAll(mesages);
			int rownum = 0;
			for (ArrayList<String> mesage : mesagesWithTitle) {
			    Row row = mesagesPage.createRow(rownum++);
			    int cellnum = 0;
			    for (Object obj : mesage) {
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
		}
		
		if(getLists){
			//create a new page lists
			HSSFSheet mesagesPage = workbook.createSheet("lists");
			
			ArrayList<String> title = new ArrayList<String>();
			title.add("name");
			title.add("menber");
			Row row = mesagesPage.createRow(0);
			int cellnum = 0;
			for (String titlepart : title){
				Cell cell = row.createCell(cellnum++);
				cell.setCellValue(titlepart);
			}

			HashMap<String, ArrayList<String>> lists = ExportController.getExportController().getListak(user);
			int rownum = 1;
			for (String list : lists.keySet()) {
			    row = mesagesPage.createRow(rownum++);
			    Cell cell = row.createCell(0);
			    cell.setCellValue(list);
			    row = mesagesPage.createRow(rownum++);
			    cellnum = 1;
			    for (Object obj : lists.get(list)) {
			        cell = row.createCell(cellnum++);
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
		}
		
		
		
		try {
			JFileChooser  jfc = new JFileChooser(); 
	        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	        jfc.showSaveDialog(null);  
	        String resultSave = jfc.getSelectedFile().getPath();  
		    FileOutputStream out = new FileOutputStream(new File(resultSave+".xls"));
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
