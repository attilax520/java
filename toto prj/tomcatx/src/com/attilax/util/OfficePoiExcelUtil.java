package com.attilax.util;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class OfficePoiExcelUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	

	private static XSSFCell getCell(XSSFRow xSSFRow1, String cellIndexAlphbat) {
		String alpString="abcdefghijklmn";
		//String numstrString="0123456789";
		char[] alp_a=alpString.toCharArray();
	//	char[] num_a=numstrString.toCharArray();
		int  alp_index=get_alp_index(cellIndexAlphbat);
		 int alp_int_idx=alp_index-1;
		return xSSFRow1.getCell(alp_int_idx);
	}

	private static String getCellValue(XSSFRow xSSFRow1, String cellIndexAlphbat) {
		String alpString="abcdefghijklmn";
		//String numstrString="0123456789";
		char[] alp_a=alpString.toCharArray();
	//	char[] num_a=numstrString.toCharArray();
		int  alp_index=get_alp_index(cellIndexAlphbat);
		 int alp_int_idx=alp_index-1;
		return xSSFRow1.getCell(alp_int_idx).getStringCellValue();
	}

	private static int get_alp_index(String cellIndexAlphbat) {
		String alpString="abcdefghijklmn";
		String numstrString="0123456789";
		char[] alp_a=alpString.toCharArray();
		
		char cia=cellIndexAlphbat.toCharArray()[0];
		int n=1;
		for (char c : alp_a) {
			if(c==cia)
				return n;
			n++;
				
		}
		return n;
	}

	public static String getCellValueStringModeForce(XSSFCell cell) {
		try {
			return cell.getStringCellValue();
		} catch (Exception e) {
			
			 cell.setCellType(XSSFCell.CELL_TYPE_STRING);  
		//	double tel= cell.getNumericCellValue();
			
		//	return String.valueOf(tel);
			
			return cell.getStringCellValue();
		}
		
	
	}


	public static String getCellValueStringModeForce(XSSFRow xSSFRow1, String cellIndexAlp) {
		 XSSFCell ce =getCell(xSSFRow1,cellIndexAlp);
		return getCellValueStringModeForce(ce);
	}

}
