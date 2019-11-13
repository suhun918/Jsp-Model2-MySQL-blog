<%@page import="com.cos.util.Gmail"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="javax.mail.Authenticator"%>
<%@page import="javax.mail.Transport"%>
<%@page import="javax.mail.Message"%>
<%@page import="javax.mail.internet.InternetAddress"%>
<%@page import="javax.mail.Address"%>
<%@page import="javax.mail.internet.MimeMessage"%>
<%@page import="javax.mail.Session"%>
<%@page import="java.util.Properties"%>
<%@page import="java.util.Properties"%>
<%@page import="com.cos.util.SHA256"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	//메일을 전송하자.
	//상대방에게
	String to = request.getParameter("email"); //(1) 이메일 받기
	//받는거
	String from = "내 gmail@gmail.com";
	//코드값이 해쉬화 될 것이다. 지금은 salt값을 그냥 고정해둠
	String code = SHA256.getEncrypt(to, "salt"); //(2) 코드 값을 SHA256으로 해시

	//사용자에게 보낼 메시지 //(3)
	String subject = "SH 블로그 회원 가입을 위한 이메일 인증 메일입니다.";
	StringBuffer sb = new StringBuffer();
	sb.append("다음 링크에 접속하여 이메일 인증을 진행해주세요. \n");
	//하이퍼 링크 주소뒤에 위에 지정해준 코드값 달아준다.
	sb.append("<a href='http://localhost:8000/blog/test/gmailCheckAction.jsp?code="+code+"'>");
	sb.append("이메일 인증하기</a>");
	String content = sb.toString();
	
	//설정 값
	Properties p = new Properties();
	p.put("mail.smtp.user", from);//(4)from
	p.put("mail.smtp.host", "smtp.googlemail.com");
	p.put("mail.smtp.port", "465"); //(5)TLS 587, SSL 465 둘다 보안방식
	p.put("mail.smtp.starttls.enable", "true");
	p.put("mail.smtp.auth", "true");
	p.put("mail.smtp.debug", "true");
	p.put("mail.smtp.socketFactory.port", "465"); 
	p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	p.put("mail.smtp.sockerFactory.fallback", "false");
	
	//이메일 전송
	try {
		Authenticator auth = new Gmail();//(6) 내가 설정한 아이디 비번 넘겨주기
		Session ses = Session.getInstance(p, auth);
		ses.setDebug(true);
		MimeMessage msg = new MimeMessage(ses);
		msg.setSubject(subject);
		Address fromAddr = new InternetAddress(from);
		msg.setFrom(fromAddr);
		Address toAddr = new InternetAddress(to);
		msg.addRecipient(Message.RecipientType.TO, toAddr);
		msg.setContent(content, "text/html; charset=UTF8");
		Transport.send(msg); // 메일전송 최종 함수
		
		session.setAttribute("mailAuth", to); //인증 비교하기 위해서 세션에 담아둔다.
	} catch (Exception e) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('이메일 인증 오류')");
		script.println("history.back();");
		script.println("</script>");
	}
	
	//정상=가만 놔둔다, 비정상 = history.back
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 자바코드가 먼저 실행되고 html이 뜨므로 아래 문구가 나오면 제대로 작동한 것이다. -->
	<h1>이메일 주소 인증 메일이 전송되었습니다. 이메일에 들어가서 인증해주세요.</h1>
</body>
</html>