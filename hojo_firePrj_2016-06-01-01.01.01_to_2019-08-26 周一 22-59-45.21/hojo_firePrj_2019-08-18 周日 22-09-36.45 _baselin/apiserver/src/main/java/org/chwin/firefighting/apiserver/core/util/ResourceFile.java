package org.chwin.firefighting.apiserver.core.util;

import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by liming on 2017/9/18.
 */
public class ResourceFile {
    public static File gtFile(String path){
        Resource resource = new ClassPathResource(path);
        try {
            return resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String gtResourceContent(String path){
        Resource resource = new ClassPathResource(path);
        try {
            String line=null;StringBuffer buffer=new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), CONSTANTS.UTF8));

            while ((line = br.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}

