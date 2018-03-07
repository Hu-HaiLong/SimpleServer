<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    <!-- 传递给StringContentServlet处理  get -->
    <form action="StringContentServlet.do" method="get">
    	<input name = "Username" type="text" value = ""/>
    	<input name = "Password" type="password" value = ""/>
    	<!-- test -->
    	<input name = "Finish" type="submit" value = "Finish"/>
    </form>
    
    <!-- 传递给ByteFileContentServlet处理  post  文件参数-->
    <form action="ByteFileContentServlet.do" method="post" enctype ="multipart/form-data">
    	<input name = "File" type="file" value = ""/>
    	<!-- test -->
    	<input name = "Finish" type="submit" value = "Finish"/>
    </form>
  </body>
</html>
