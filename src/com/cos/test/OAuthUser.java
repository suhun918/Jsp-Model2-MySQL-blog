package com.cos.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class OAuthUser {
	private int id;
	private String access_Token;
	private String refresh_Token;
	private String token_type;
	private String exprie_in;
	
	public OAuthUser() {
		// TODO Auto-generated constructor stub
	}

	public OAuthUser(int id, String access_Token, String refresh_Token, String token_type, String exprie_in) {
		super();
		this.id = id;
		this.access_Token = access_Token;
		this.refresh_Token = refresh_Token;
		this.token_type = token_type;
		this.exprie_in = exprie_in;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccess_Token() {
		return access_Token;
	}

	public void setAccess_Token(String access_Token) {
		this.access_Token = access_Token;
	}

	public String getRefresh_Token() {
		return refresh_Token;
	}

	public void setRefresh_Token(String refresh_Token) {
		this.refresh_Token = refresh_Token;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getExprie_in() {
		return exprie_in;
	}

	public void setExprie_in(String exprie_in) {
		this.exprie_in = exprie_in;
	}
	
	
}
