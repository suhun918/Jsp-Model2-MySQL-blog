package com.cos.action.comment;

import com.cos.action.Action;
import com.cos.action.user.UserJoinAction;
import com.cos.action.user.UserLoginAction;
import com.cos.action.user.UserLogoutAction;
import com.cos.action.user.UserUpdateAction;

public class CommentFactory {
	public static Action getAction(String cmd) {
		if(cmd.equals("delete")) {
			return new CommentDeleteAction();
		}if(cmd.equals("write")) {
			return new CommentWriteAction();
		}
		return null;
	}
}
