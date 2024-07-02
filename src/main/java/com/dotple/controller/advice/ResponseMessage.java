package com.dotple.controller.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseMessage<T> {

	private int code;
	private String message;
	private T result;

	public ResponseMessage(T data) {
		this.code = ResponseCode.C2000.getCode();
		this.message = ResponseCode.C2000.getMessage();
		this.result = data;
	}

	public ResponseMessage(ResponseCode status, T data) {
		this.code = status.getCode();
		this.message = status.getMessage();
		this.result = data;
	}
}
