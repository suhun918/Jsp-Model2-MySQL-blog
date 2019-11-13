package com.cos.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class Script {

	public static void back(HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('Failed');");// 나중에 보내는 것도 UTF-8로 가게 세팅하는거 알려달라고 하자
			out.println("history.back();");
			out.println("</script>");
			out.flush();// 버퍼를 비운다.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
