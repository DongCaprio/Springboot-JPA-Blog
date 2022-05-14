package com.cos.blog.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder encode;

	// 밑에 login펑션에서 주석처리한것처럼 해도됨 @Autowired안하고
	/*	@Autowired
		private HttpSession session; */

	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiContorller : save호출됨");
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/user") //json데이터를 위해서 @RequestBody를 하는것***
	public ResponseDto<Integer> update(@RequestBody User user){
		userService.회원수정(user);
		//여기에 트랜잭션이 종료되기 때문에 DB에 값은 변경이 되지만 
		// 하지만 세션값은 변경되지 않은 상태이기 때문에
		// 세션에서 가져온 값은 계속 유지가 된다 
		System.out.println("---------------잘못됨---------------------");
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

}
