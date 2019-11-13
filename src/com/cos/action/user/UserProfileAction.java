package com.cos.action.user;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.action.Action;
import com.cos.dao.UserDao;
import com.cos.model.User;
import com.cos.util.Script;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class UserProfileAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// java 파일에서는 JAVA 에서 request.getRealPath("/"); 는 deprecated되었다.
		ServletContext application = request.getServletContext();
		String path = application.getRealPath("media");
		
		try {
			MultipartRequest multi = new MultipartRequest(
					request,
					path, // 경로
					1024 * 1024 * 2, // 2MB
					"UTF-8",
					new DefaultFileRenamePolicy()
					);
			
			String username = multi.getParameter("username");
			String filename = multi.getFilesystemName("userProfile");
			//String originFilename = multi.getParameter("originFilename");
			String contextPath = application.getContextPath();
			String filepath = contextPath + "/media/" + filename;
			
			
			User user = new User();
			user.setUserProfile(filepath);
			user.setUsername(username);
			
			UserDao dao = new UserDao();
			int result = dao.updateUserProfile(user);
			
			if(result == 1) {
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				response.sendRedirect("/blog/index.jsp");
			}else {
				Script.back(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
