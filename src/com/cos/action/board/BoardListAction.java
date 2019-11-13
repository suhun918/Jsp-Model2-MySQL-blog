package com.cos.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.action.Action;
import com.cos.dao.BoardDao;
import com.cos.model.Board;
import com.cos.util.Utils;

public class BoardListAction implements Action {
	private int count;
	private final String TAG = "BoardListAction :";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("page") == null)
			return;

		int page = Integer.parseInt(request.getParameter("page"));

		// page<=0 혹은 page> maxNum버튼 비활성화
		if (page <= 0) {
			page = 1;
		}

		// Dao 연결
		BoardDao bDao = new BoardDao();
		// Dao에 만들어줘야한다.
		List<Board> boards = null;// (3)이제 얘가 user객체도 들고있는 것이다 //bDao.findAll(page)
		List<Board> hotBoards = bDao.findOrderByReadCountDesc();
		
		
		// search와 list를 분기
		if (request.getParameter("search") == null || request.getParameter("search").equals("")) {
			boards = bDao.findAll(page);// paging하기
			count = bDao.findAll();
			request.setAttribute("search", null);
			
		} else {
			String search = request.getParameter("search");
			boards = bDao.findAll(page, search);
			count = bDao.findAll(search);
			request.setAttribute("search", search);
			//request.setAttribute("count", count);
		}

		Utils.setPreviewImg(boards);
		// PreviewContent가 이미지 태그를 제거하기때문에 preview이미지를 위로 올려야해
		Utils.setPreviewContent(boards);
		Utils.setPreviewImg(hotBoards);

		// request에 담기
		request.setAttribute("boards", boards);
		request.setAttribute("hotBoards", hotBoards);
		request.setAttribute("count", count);

		// send 되는순간 위의 request 정보는 사라지기 때문에 아래의 작업을 해서 보존한다.
		// 생성된 것을 톰켓에 덮어씌워서 보존하는 방식임
		RequestDispatcher dis = request.getRequestDispatcher("board/list.jsp");
		dis.forward(request, response);

	}

}
