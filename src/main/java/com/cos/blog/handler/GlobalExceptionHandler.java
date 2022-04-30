package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice //이 어노테이션 사용시에 모든 Exception이 이쪽메소드로 흘러들어오게 된다
@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = Exception.class) // 이 괄호안이 있어야지 IllegalArgumentException 의 에러결과가 밑에줄의 e에 들어가게 된다 
	public String handleArgumentException(IllegalArgumentException e) {
		return "<h1>"+e.getMessage()+"</h1>";
	}
}
