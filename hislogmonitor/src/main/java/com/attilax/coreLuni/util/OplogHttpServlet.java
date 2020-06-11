package com.attilax.coreLuni.util;
 
import java.io.IOException;
import java.io.PrintWriter;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 

//   com.attilax.oplog.util.OplogHttpServlet
@WebServlet(name="restapi", urlPatterns={"/restapi","/userInfo"},loadOnStartup=1)
public class OplogHttpServlet extends HttpServlet{
    private static final String DEFAULT_USER="rzm";
     
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      //  String user=request.getParameter("user");
    	
    	
    	
         
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer=response.getWriter();
        writer.append(new RestUtil().index2(request, response));
       
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }
}