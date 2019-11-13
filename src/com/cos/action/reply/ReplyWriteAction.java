package com.cos.action.reply;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.action.Action;
import com.cos.dao.ReplyDao;
import com.cos.model.Reply;
import com.cos.util.Script;
import com.google.gson.Gson;

public class ReplyWriteAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int commentId = Integer.parseInt(request.getParameter("commentId"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		String content = request.getParameter("content");

		Reply replyForm = new Reply();
		replyForm.setCommentId(commentId);
		replyForm.setUserId(userId);
		replyForm.setContent(content);

		ReplyDao dao = new ReplyDao();
		int result = dao.save(replyForm);

		if (result == 1) {
			//다들고 가서 뿌려줄것이라서 요것
			List<Reply> replys = dao.findByCommentId(commentId);

			Gson gson = new Gson();
			String ReplyJson = gson.toJson(replys);

			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(ReplyJson);
			out.flush();
		} else {
			Script.back(response);
		}
	}
}
