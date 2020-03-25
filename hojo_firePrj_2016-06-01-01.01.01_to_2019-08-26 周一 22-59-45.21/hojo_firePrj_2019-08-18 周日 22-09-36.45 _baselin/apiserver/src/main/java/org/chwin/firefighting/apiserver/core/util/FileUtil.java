package org.chwin.firefighting.apiserver.core.util;

import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.CONSTANTS;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liming on 2017/9/26.
 */
public class FileUtil {
    public static String readTxt(String path){
        File file=new File(path);
        return readTxt(file);
    }

    public static String readTxt(File file){
        try{
            if(file.isFile() && file.exists()){
                InputStreamReader read = new InputStreamReader( new FileInputStream(file), CONSTANTS.UTF8);
                BufferedReader bufferedReader = new BufferedReader(read);
                StringBuffer buffer = new StringBuffer();
                String lineTxt="";
                while((lineTxt = bufferedReader.readLine()) != null){
                    buffer.append(lineTxt).append("\n");
                }
                read.close();

                return buffer.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<File> ls(String path,String ext){
        File dir=new File(path);
        List<File> list=new ArrayList<File>();
        if(dir.isDirectory()) {

            File[] dirs = dir.listFiles();

            for(int i = 0;i < dirs.length ; i++){
                File subItem = dirs[i];
                if(subItem.isFile())
                    continue;
                list.addAll(ls(subItem.getPath(),ext));
            }
            File[] files = dir.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    if (ext != null && StringUtils.endsWithIgnoreCase(file.getName(), ext))
                        return true;
                    return false;
                }
            });
            list.addAll(Arrays.asList(files));
        }

        return list;
    }
}
