package com.attilax.log;

import java.io.Console;
import java.io.PrintStream;  

 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  
  
  /**
   * apache logign haosyo geng log4j sh yyode ..
   * @author Administrator
   *
   */
public class StdOutErrRedirector4slf4j {  
 
    
    public static void main(String[] args) {
   
    //	 StdOutErrRedirect.redirectSystemOutAndErrToLog();
      	StdOutErrRedirector4slf4j.redirectSystemOutAndErrToLog_slf4j();
     	System.out.println("--logxxx r525");
	}
  
 
    
    public static void redirectSystemOutAndErrToLog_slf4j() {  
    	Logger logger = LoggerFactory.getLogger(StdOutErrRedirector4slf4j.class);  
    //	 Logger logger = LoggerFactory.getLogger("Com_jacob_office_Util");
        PrintStream printStreamForOut = createLoggingWrapper(System.out, false,logger);  
        PrintStream printStreamForErr = createLoggingWrapper(System.out, true,logger);  
        System.setOut(printStreamForOut);  
        System.setErr(printStreamForErr);  
    }  
  
 
    
    public static PrintStream createLoggingWrapper(final PrintStream printStream, final boolean isErr,   	Logger logger) {  
        return new PrintStream(printStream) {  
            @Override  
            public void print(final String string) {  
                if (!isErr){  
                    logger.debug(string);  
                }else{  
                    logger.error(string);  
                }  
            }  
            @Override  
            public void print(boolean b) {  
                if (!isErr){  
                    logger.debug(Boolean.valueOf(b).toString());  
                }else{  
                    logger.error(Boolean.valueOf(b).toString());  
                }  
            }  
            @Override  
            public void print(char c) {  
                if (!isErr){  
                    logger.debug(Character.valueOf(c).toString());  
                }else{  
                    logger.error(Character.valueOf(c).toString());  
                }  
            }  
            @Override  
            public void print(int i) {  
                if (!isErr){  
                    logger.debug(String.valueOf(i));  
                }else{  
                    logger.error(String.valueOf(i));  
                }  
            }  
            @Override  
            public void print(long l) {  
                if (!isErr){  
                    logger.debug(String.valueOf(l));  
                }else{  
                    logger.error(String.valueOf(l));  
                }  
            }  
            @Override  
            public void print(float f) {  
                if (!isErr){  
                    logger.debug(String.valueOf(f));  
                }else{  
                    logger.error(String.valueOf(f));  
                }  
            }  
            @Override  
            public void print(double d) {  
                if (!isErr){  
                    logger.debug(String.valueOf(d));  
                }else{  
                    logger.error(String.valueOf(d));  
                }  
            }  
            @Override  
            public void print(char[] x) {  
                if (!isErr){  
                    logger.debug(x == null ? null : new String(x));  
                }else{  
                    logger.error(x == null ? null : new String(x));  
                }  
            }  
            @Override  
            public void print(Object obj) {  
                if (!isErr){  
                    logger.debug(obj.toString());  
                }else{  
                    logger.error(obj.toString());  
                }  
            }  
        };  
    }  
}  