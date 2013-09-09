<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<body>
	<%
	out.println("AUTH_TOKEN은 "+ (String)request.getAttribute("auth_token") +" 입니다.");
	out.println("보낸 결과 코드는 "+  (String)request.getAttribute("send") + " 입다.");
	%>
</body>
