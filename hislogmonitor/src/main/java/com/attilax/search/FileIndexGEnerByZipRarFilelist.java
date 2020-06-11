package com.attilax.search;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.attilax.io.DirTraveService;
import com.attilax.io.FileService;
import com.attilax.io.PathService;
import com.attilax.io.pathx;
import com.attilax.util.timestampUtil;
import com.cnhis.cloudhealth.module.log.Cls1;
import com.google.common.base.Function;
import com.google.common.collect.Maps;

public class FileIndexGEnerByZipRarFilelist {
	public static Logger logger = Logger.getLogger(Cls1.class);

	public static void main(String[] args) {
		DirTraveService dts = new DirTraveService();
		final String dir = "C:\\Users\\attilax\\Documents\\ati doc index ziped forbek v2 s222\\sumdoc all  docver n txtver tit list";
		final String dirdest = "c:\\00txtmodeIndexdir\\ziprar_filelist_2000-201710";
		dts.traveV4Vs427(dir, new Function<File, Object>() {

			@Override
			public Object apply(File f) {
			//	if(f.getName().endsWith("zip.txt"))
			//		return null;//continue;
				try {
				    new FileIndexGEnerByZipRarFilelist().traveSingle(dirdest,  f);
				} catch (IOException e) {

					e.printStackTrace();
					logger.error(e);
				}
				return null;
			}

		
		}, new Function<Integer, Object>() {

			@Override
			public Object apply(Integer cnt_index) {
				// Map m=Maps.newConcurrentMap();m.put("index", cnt_index)
				logger.info("cnt_index:" + cnt_index);
				return null;
			}
		});
	}
	  String stat="ini";
	private   void traveSingle( String dirdest, File f) throws IOException {
		logger.info("arg0:" + f.getAbsolutePath());
		// ------------
		List<String> lines = FileUtils.readLines(f,"gbk");
		for (String line : lines) {
			line=line.trim();
			if(line.trim().length()==0)
				continue;
			String startTag = "-----------";
			if( !line.trim().startsWith(startTag) && stat.equals("ini") )
			{
			 
				continue;
			}
			
			if( line.trim().startsWith(startTag) && stat.equals("ini") )
			{
				stat="nowing";
				continue;
			}
		 
			if( line.trim().startsWith(startTag) && stat.equals("nowing") )
			{
				stat="end";
				break;
			}
			if(  stat.equals("end") )
			{
			 
				break;
			}
			
			
				//////////--stat  nowing
			try {
				String filename = getFname(line, f.getAbsolutePath());
				logger.info("zip>file:" +f.getName()+">>"+ filename);
				filename=FileService.fileNameEncode_readableBest(filename);
				// -----------newfile
				 String newFileTxtmode4index=filename+".index.txt";
			 String newfilepath=dirdest+"\\"+f.getName()+"\\"+newFileTxtmode4index;

				String txtcontext = "data" + timestampUtil.gettimeStamp_millsec();
				logger.info("newfilepath:" + newfilepath);
				logger.info("txtcontext:" + txtcontext);
			
				FileUtils.write(new File(newfilepath), txtcontext);

			} catch (Exception e) {

				e.printStackTrace();logger.error(e);
			}

		}
	}

	protected static String getFname(String line, String absolutePath) {
		if (absolutePath.endsWith("zip.txt"))
			return line.substring(54);
		if (absolutePath.endsWith("rar.txt"))
			return line.substring(37);
		throw new RuntimeException("not zip and rar txt");
	}

}
