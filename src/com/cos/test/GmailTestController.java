package com.cos.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/test/gmail")
public class GmailTestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GmailTestController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		//1. DB에 INSERT했다고 가정!! - emailCheck 디폴트 = 0
		
		//ID값 가져와서!!
		
		//2.
		response.sendRedirect("/blog/test/gmailSendAction.jsp?email="+email);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
