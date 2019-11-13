package com.cos.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

// 메일로 접근(사용) 할 때 인증 : 구글 아이디, 구글 비밀번호
public class Gmail extends Authenticator {
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		//깃에 넣어 관리하기 위해서 비밀번호는 다른데 빼두고 사용한다.
		return new PasswordAuthentication("a27513528@gmail.com", Password.GOOGLEPASSWORD);
	}
}
