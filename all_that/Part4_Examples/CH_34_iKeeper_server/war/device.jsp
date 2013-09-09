<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
</head>
<body>
	<%
		Key key = (Key)request.getAttribute("key");
		out.println("Key는 "+key.getName() + " 입니다.");
	%>
</body>
</html>