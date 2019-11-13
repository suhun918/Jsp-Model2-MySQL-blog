<%@page import="java.io.PrintWriter"%>
<%@page import="com.cos.util.SHA256"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	//code값 받기
	String code = request.getParameter("code");
	String email = request.getParameter("email");
	String username = request.getParameter("username");
    
    	//return code 값이랑 send code 값을 비교해서 동일하면
	boolean rightCode = 
			//3항연산자 맞으면 true 틀리면 false
			//지금은 메일id값 고정해놨지만 나중엔 구현해줘야함
			SHA256.getEncrypt(email, "salt").equals(code)?true:false;
	
    	
    	
	PrintWriter script = response.getWriter();
	//rightCode가 true
   	if(rightCode){
   		//DB에 칼럼 emailCheck(Number)1 = 인증, 0= 미인증 -> 1을 업데이트 해준다.
   		script.println("<script>");
		script.println("alert('이메일 인증에 성공하였습니다.')");
		script.println("location.href='/blog/user?cmd=admit&username="+username+"'");
		script.println("</script>");
	} else{
		script.println("<script>");
		script.println("alert('이메일 인증을 실패하였습니다.')");
		script.println("location.href='/blog/gmail/errorPage.jsp'");
		script.println("</script>");
	}



	
	
	
	//인증완료 로그인 페이지로 이등
	
	
	//미인증 error 페이지로 이동
	
%>
