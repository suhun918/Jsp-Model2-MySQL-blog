package com.cos.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.action.Action;

public class UserLogoutAction	implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//세션 만료
		HttpSession session = request.getSession();
		session.invalidate();// 세션 무효화(세션 전부)
		//session.removeAttribute("username");//원하는 세션만 날리기
		
		//list로 이동
		response.sendRedirect("/blog/index.jsp");
	}
}
