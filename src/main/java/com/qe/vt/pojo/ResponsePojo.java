package com.qe.vt.pojo;

import java.util.List;

import lombok.Data;

@Data
public class ResponsePojo {
	private List<String> messages;
	private String status;
	private String message;
	private String timestamp;
	private String error;
	private String exception;
	private String path;
}
