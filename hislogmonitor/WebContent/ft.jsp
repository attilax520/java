<%@ page contentType="text/html; charset=gb2312" language="java" import="java.io.*"   %>  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">  
<title>Untitled Document</title>  
</head>  
  
<body>  
当前WEB应用的物理路径：<%=application.getRealPath("/")%><BR>  


request.getRequestURI()：<%=request.getRequestURI()%><BR> 
当前你求请的JSP文件的物理路径：<%=application.getRealPath(request.getRequestURI())%><BR>  
<%  
 


com.attilax.net.ftp.ftpapache.main(null);


%>  
</body>  
</html> 