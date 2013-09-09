<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<%
		List<Entity> list = (List<Entity>)request.getAttribute("list");
		if(list.isEmpty()){
			%>
				<p>No Device List</p>
			<%
		}else{
			for(Entity ety:list){
			%>
				
				Device_id:<%=ety.getProperty("device_id") %> 
				Registration_id:<%=ety.getProperty("registration_id") %> 
				<br/>
			<%
			}
		}
	%>
	<br/>
	아래의 폼에 위의 목록에서 나온 Device_id, Registration_id를 입력하고 전송버튼을 누르시면 메시지가 C2DM을 통해 발송됩니다.<br/>
	<form name="form1" method="post" action="/send">
		device_id : <input type="text" name="device_id" size="50" value=""/><br/>
		registration_id :<input type="text" name="registration_id" size="50" value=""/><br/>
		message : <input type="text" name="message" size="50" maxlength="50"/><br/>
	</form>	
		<input type="button" name="send" value="보내기" onclick="javascript:document.form1.submit();"/>
	

</body>
</html>