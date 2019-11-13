package com.cos.model;

import java.sql.Timestamp;

public class Comment {
	
	private ResponseData responseData = new ResponseData();//DB 와 상관없음
	private int id; //PK
	private int userId; //FK
	private int boardId; //FK
	private String content;
	private Timestamp createDate;
	private User user = new User(); // DB와 상관없음
	
	public Comment() {
		// TODO Auto-generated constructor stub
	}

	public Comment(ResponseData responseData, int id, int userId, int boardId, String content, Timestamp createDate,
			User user) {
		super();
		this.responseData = responseData;
		this.id = id;
		this.userId = userId;
		this.boardId = boardId;
		this.content = content;
		this.createDate = createDate;
		this.user = user;
	}

	public ResponseData getResponseData() {
		return responseData;
	}

	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
