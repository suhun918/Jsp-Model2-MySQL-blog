package com.cos.action.reply;

import com.cos.action.Action;
import com.cos.action.user.UserJoinAction;
import com.cos.action.user.UserLoginAction;
import com.cos.action.user.UserLogoutAction;
import com.cos.action.user.UserUpdateAction;

public class ReplyFactory {
	public static Action getAction(String cmd) {
		if (cmd.equals("delete")) {
			return new ReplyDeleteAction();
		}else if (cmd.equals("write")) {
			return new ReplyWriteAction();
		}else if(cmd.equals("list")) {
			return new ReplyListAction();
		}
		return null;
	}
}
