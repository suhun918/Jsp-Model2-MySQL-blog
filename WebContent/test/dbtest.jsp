<%@page import="com.cos.dao.DBConn"%>

<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String msg = "";
		Connection conn = DBConn.getConnection();
		if (conn == null) {
			msg = "DB연결 실패";
		} else {
			msg = "DB연결 성공";
		}
	%>
	<h1><%=msg%></h1>
</body>
</html>