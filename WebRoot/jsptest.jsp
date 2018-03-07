<%@ page contentType="text/html;charset=gbk"%>
<html>
<Head>
<title>测试jsp</title>
</Head>
<body>
现在时间是:
<%
java.util.Date date=new java.util.Date();
out.print(date);
%>
</body>
</html>