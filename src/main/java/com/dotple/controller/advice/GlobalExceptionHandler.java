package com.dotple.controller.advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ResponseMessage<String>> HandleCustomException(CustomException e) {

		ResponseCode responseCode = e.getResponseCode();

		return handleExceptionInternal(responseCode);
	}

	// Optional.get()에 대한 예외 처리
	@ExceptionHandler
	public ResponseEntity<ResponseMessage<String>> HandleNoSuchElementException(NoSuchElementException e) {
		return handleExceptionInternal(ResponseCode.C4040);
	}

	// 적절하지 않은 데이터가 DB로 들어갈 뻔한 경우. 발생해서는 안 됨!
	@ExceptionHandler
	public ResponseEntity<ResponseMessage<String>> HandleDataIntegrityViolationException(DataIntegrityViolationException e) {
		return handleExceptionInternal(ResponseCode.C5001);
	}

	private ResponseEntity<ResponseMessage<String>> handleExceptionInternal (ResponseCode responseCode) {
		return ResponseEntity.status(responseCode.getStatus()).body(new ResponseMessage<>(responseCode, ""));
	}

	@Override
	public ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request){

		return ResponseEntity.status(500).body(new ResponseMessage<>(ResponseCode.C5000, ""));
	}
}
