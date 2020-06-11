package com.attilax.shell;

import java.io.File;
import java.io.IOException;

import com.attilax.str.Strutil;
import com.attilax.util.ExUtil;

public class ProcessUtil {

	public static Process start(String lastcmd, String cwd) {
		// TODO Auto-generated method stub
		try {
			File directory;
			if(cwd==null ||  cwd.trim().length()==0)
				directory=null;
			else
			  directory = new File(cwd);
			return new ProcessBuilder(Strutil.toStrArray(lastcmd)).directory(directory) .start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ExUtil.throwExV2(e);
		}
		return null;
	}
	
	@Deprecated
	public static Process startV2(String lastcmd, String cwd) {
		// TODO Auto-generated method stub
		try {
			File directory;
			if(cwd==null ||  cwd.trim().length()==0)
				directory=null;
			else
			  directory = new File(cwd);
			return new ProcessBuilder(Strutil.toStrArray(lastcmd)).directory(directory) .start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ExUtil.throwExV2(e);
		}
		return null;
	}

}
