package com.cos.model;

public class ResponseData {
	private int statusCode; //1, -1
	private String status; // "ok","fail"
	private String statusMessage; // "Paring error", "Page not found"
	
	public ResponseData() {
		// TODO Auto-generated constructor stub
	}

	public ResponseData(int statusCode, String status, String statusMessage) {
		super();
		this.statusCode = statusCode;
		this.status = status;
		this.statusMessage = statusMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	
}
