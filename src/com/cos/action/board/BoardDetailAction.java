package com.cos.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.action.Action;
import com.cos.dao.BoardDao;
import com.cos.dao.CommentDao;
import com.cos.model.Board;
import com.cos.model.Comment;
import com.cos.model.User;
import com.cos.util.Script;
import com.cos.util.Utils;

public class BoardDetailAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// id 가 null이거나 공백이면 리턴 안걸어준 페이지에 다 걸어야 한다.
		if (request.getParameter("id") == null || request.getParameter("id").equals(""))
			return;

		int id = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		String username = "";
		String cookieUsername = "";
		try {
			username = user.getUsername();
		} catch (Exception e) {
			System.out.println("user정보 없음");
		}

		if (request.getCookies() != null) {
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				System.out.println(cookie.getName());
				System.out.println("   " + cookie.getMaxAge());
				if (cookie.getName().equals(id + "board" + username)) {
					cookieUsername = cookie.getValue();
				}

			}
		}

		BoardDao dao = new BoardDao();
		Board board = dao.findById(id);// 얘가 user도 들고있게 됐다

		CommentDao commentDao = new CommentDao();

		List<Comment> comments = commentDao.findByBoardId(id);

		if (board != null) {
			// 쿠키를 저장해서(1일) 쿠키가 있을 때 새로고침 방지는(나중에)

			// 조회수 증가
			int result = -1;
			if (username.equals("")) {// username이 없을 때 올라가지 않음
				result = 1;
			} else if (cookieUsername.equals("")) {// 쿠키에 유저네임 값이 없음
				Cookie c = new Cookie(id + "board" + username, username);
				c.setMaxAge(86400);
				response.addCookie(c);
				result = dao.increaseReadCount(id);
			} else if (!cookieUsername.equals(username)) {// 쿠키에 유저네임 값이 없음
				Cookie c = new Cookie(id + "board" + username, username);
				c.setMaxAge(86400);
				response.addCookie(c);
				result = dao.increaseReadCount(id);
			} else if (cookieUsername.equals(username)) {// 쿠키랑 username 같음
				result = 1;
			}

			// Script.back과 주소이동 충돌 막기 위해서 if 안에 넣어줌
			if (result == 1) {
				// 유튜브 주소 파싱
				Utils.setPreviewYoutube(board);

				// board를 request에 담고 dispatcher로 이동
				request.setAttribute("board", board);
				request.setAttribute("comments", comments);
				RequestDispatcher dis = request.getRequestDispatcher("board/detail.jsp");
				dis.forward(request, response);
			} else {
				Script.back(response);
			}

		} else {
			Script.back(response);
		}

	}
}
