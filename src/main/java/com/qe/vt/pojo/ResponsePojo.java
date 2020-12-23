package com.qe.vt.pojo;

import java.util.List;

import lombok.Data;

@Data
public class ResponsePojo {
	private String status;
	private List<String> messages;
}
