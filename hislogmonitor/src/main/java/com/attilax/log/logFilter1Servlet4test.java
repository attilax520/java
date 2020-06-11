package com.attilax.log;
 
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
 

//   com.attilax.oplog.util.OplogHttpServlet
//    http://localhost/restapi2?parm1=aaa1
@WebServlet(name="restapi2", urlPatterns={"/restapi2","/userInfo2"},loadOnStartup=1)
public class logFilter1Servlet4test extends HttpServlet{
    private static final String DEFAULT_USER="rzm";
     
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      //  String user=request.getParameter("user");
    	
    	
    	
         
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer=response.getWriter();
        
        
        String s=FileUtils.readFileToString(new File("c:\\d\\http%3A%2F%2Flocalhost%3A8080%2Fclidoctorweb%2Fcloud%2Fclinewadvice%2FzhenLiaoPinCiList.txt"));
        writer.append(s);
       
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }
}