package com.cos.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.action.Action;
import com.cos.dao.UserDao;

public class UserAdmitAction implements Action{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		
		UserDao dao = new UserDao();
		
		dao.setEmailCheckByUsername(username);
		
		response.sendRedirect("/blog/user/login.jsp");
		
	}

}
