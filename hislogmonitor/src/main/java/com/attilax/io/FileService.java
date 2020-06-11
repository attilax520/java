package com.attilax.io;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class FileService {
	
	public static void main(String[] args) throws IOException {
		String oriname="C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\io\\filenameEncodeTest.txt";
		String t=FileUtils.readFileToString(new File(oriname));
		String newfilename=fileNameEncode_readableBest(t);
		FileUtils.writeStringToFile(new File("C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\io\\"+newfilename+".txt"), "data");
	System.out.println("--");
	}

/**
 * but cant convert to ori	
 * @param filenameOri
 * @return
 */
	public static String fileNameEncode_readableBest(String filenameOri) {
		filenameOri=filenameOri.replaceAll("\\\\", "、");
	 	filenameOri=filenameOri.replaceAll("\\/", "、"); 
		filenameOri=filenameOri.replaceAll("\\|", "、"); 
		filenameOri=filenameOri.replaceAll("\\*", "。"); 
		filenameOri=filenameOri.replaceAll("\\?", "？"); 
		filenameOri=filenameOri.replaceAll("\"", "“"); 
		filenameOri=filenameOri.replaceAll("<", "《"); 
			filenameOri=filenameOri.replaceAll(">", "》"); 
	 		filenameOri=filenameOri.replaceAll(":", "："); 
		return filenameOri;
	}
	
//	
//	public static String fileNameEncode(String filenameOri) {
//		// /\:* <>\"|
//		if (filenameOri.equals("."))
//			return "%2E";
//		if (filenameOri.equals(".."))
//			return "%2E%2E";
//		Map<String, String> mp = (Map<String, String>) new ClosureNoExcpt() {
//
//			@Override
//			public Object execute(final Object arg0) {
//				final Map<String, String> mp = new HashMap<String, String>() {
//					{
//						this.put("*", "%2A");
//
//					}
//				};
//				final String[] as = strUtil.SplitByNone("/\\:?<>\"|");
//
//				for (final String s : as) {
//					try {
//						mp.put(s, URLEncoder.encode(s, "utf-8"));
//
//					} catch (final UnsupportedEncodingException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				return mp;
//			}
//		}.execute(null);
//		String[] as = strUtil.SplitByNone(filenameOri);
//		String fname2 = "";
//		for (String s : as) {
//			fname2 += mp.get(s) == null ? s : mp.get(s);
//		}
//		return fname2;
//	}

	public static void exists_waitforGeneFile(String sqlpath, int timeoutMills) {

		int pasttime = 0;
		while (true) {
			if (new File(sqlpath).exists())
				break;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pasttime = pasttime + 1000;
			if (pasttime > timeoutMills)
				throw new RuntimeException("timeout:" + timeoutMills);
		}

	}

	public static void writeByteArrayToFile(String pathname, byte[] data_bytearr) {
		try {
			  FileUtils.forceMkdir(new File(pathname).getParentFile());
			FileUtils.writeByteArrayToFile(new File(pathname), data_bytearr);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		 
      
		
	}

}
