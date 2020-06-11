package com.attilax.io;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class TextFileService {
	
	public TextFileService(String f) {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		String f="C:\\0wkspc\\oploggerPrj\\aaa12018-04-09 14_31_55_386.sql";
		TextFileService tfs=new TextFileService(f);
		String t=FileUtils.readFileToString(new File(f));
		int fromIndex=t.indexOf("CREATE TABLE");
		int end=t.indexOf(';', fromIndex);
		System.out.println( t.substring(fromIndex, end) );
				
		
	}

	private int indexOf(String string) {
		// TODO Auto-generated method stub
		return 0;
	}

}
