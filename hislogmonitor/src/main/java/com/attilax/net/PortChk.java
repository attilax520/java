package com.attilax.net;

/**
 * @author MrBread
 * @date 2017年6月18日
 * @time 下午3:14:05
 * @project_name TestSocket
 * 功能：检测本机端口是否已经被使用用
 */
 

import java.io.IOException;  
import java.net.InetAddress;  
import java.net.Socket;  
import java.net.UnknownHostException;

import org.junit.Test;

import com.attilax.util.ExUtil;  
@SuppressWarnings("all")
public class PortChk {  
    //start--end是所要检测的端口范围
    
    public static void main(String args[]){
    	
    	
    	  int start=8443;
      
          System.out.println( PortChk.getNonUsePort(start));
      
      
    }
    public static int getNonUsePort(int start) {
        int end=start+1024;
    	  for(int i=start;i<=end;i++){
              
              if(isLocalPortUsing(i)){
            	  System.out.println(" posrt useed:"+i);
                 continue;
              }
              return i;
            
    	  }
		return end;
     
	}
	/**
     * 测试本机端口是否被使用
     * @param port
     * @return
     */
 
    public static boolean isLocalPortUsing(int port){  
        boolean flag = true;  
        try {
            //如果该端口还在使用则返回true,否则返回false,127.0.0.1代表本机
            flag = isPortUsing("127.0.0.1", port);  
        } catch (Exception e) {  
        	return false;
        }  
        return flag;  
    }  
    
    
    @Test
    public   void  test_isPortUsing ()
    {
    //	System.out.println(  PortChk. isPortUsing_local(22));
    	try {
			System.out.println(  PortChk. isPortUsing("10.0.0.10",22));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
    }
    
    
    @Test
    public   void  test_isPortUsing_local()
    {
    //	System.out.println(  PortChk. isPortUsing_local(22));
    //	System.out.println(  PortChk. isPortUsing_local(22));
	 
    }
    
    public static boolean isPortUsing_local_retBool(int port){  
    	 
        
        //如果该端口还在使用则返回true,否则返回false,127.0.0.1代表本机
        try {
        	isPortUsing_local(port);
		 
		} catch (Exception e) {
			return false;
		}  
    
    return true;  
}  
    
    
    public static void isPortUsing_local(int port){  
 
         
            //如果该端口还在使用则返回true,否则返回false,127.0.0.1代表本机
            try {
				  isPortUsing("127.0.0.1", port);
			} catch (UnknownHostException e) {
				ExUtil.throwExV2(e);
			} catch (IOException e) {
				ExUtil.throwExV2(e);
			}  
        
       
    }  
    /*** 
     * 测试主机Host的port端口是否被使用
     * @param host 
     * @param port 
     * @throws IOException 
     */  
    public static boolean isPortUsing(String host,int port) throws IOException{  
       
        InetAddress Address = InetAddress.getByName(host);  
        
           
			Socket socket = new Socket(Address,port);  //建立一个Socket连接
            
         
        return true;  
    }  
}  