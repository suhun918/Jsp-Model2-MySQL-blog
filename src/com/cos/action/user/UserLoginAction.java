package com.cos.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.action.Action;
import com.cos.dao.UserDao;
import com.cos.model.User;
import com.cos.util.SHA256;
import com.cos.util.Script;

public class UserLoginAction implements Action {

	private static final String TAG = "UserLoginAction :";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 유효성 검사를 나중에 해주자.
		String username = request.getParameter("username");
		String rawPassword = request.getParameter("password");
		String rememberMe = request.getParameter("rememberMe");
		String password = SHA256.getEncrypt(rawPassword, "cos");

		// 잘 찍히는지 테스트용
//		System.out.println(TAG + "username :" + username);
//		System.out.println(TAG + "password :" + password);
//		System.out.println(TAG + "rememberMe :" + rememberMe);

		UserDao dao = new UserDao();
		int result = dao.findByUsernameAndPassword(username, password);

		if (result == 1) {
			// 쿠키 저장
			int resultemailCheck = dao.emailCheck(username);
			if (resultemailCheck == 1) {
				System.out.println("인증되었습니다.");
				if (rememberMe != null) {
					System.out.println(TAG + "쿠키 저장");
					Cookie c = new Cookie("username", username);
					// 쿠키는 타임을 따로 지정해줘야 한다.
					c.setMaxAge(6000);// 100분
					response.addCookie(c);
				} else {
					System.out.println(TAG + "쿠키 삭제");
					Cookie c = new Cookie("username", null);
					c.setMaxAge(0);// 즉시 사라짐
					response.addCookie(c);
				}

				User user = dao.findByUsername(username);
				// 세션 등록(내가 new안해도 이미 떠있음 싱글톤)
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				// 리스트로 돌아가게 할건데 나중에 index를 리스트페이지로 보낼거다.
				response.sendRedirect("/blog/index.jsp");

			} else {
				System.out.println("인증하세요");
				// 이메일 인증 안된 유저가 접속 시 어떻게 처리해줄 지 마무리
				response.sendRedirect("/blog/gmail/emailCheckFail.jsp");
			}

		} else {
			Script.back(response);
		}
	}
}
