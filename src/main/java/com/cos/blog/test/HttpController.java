package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(HTML 파일)
//@Controller

//사용자가 요청 -> 응답(Data)
@RestController
public class HttpController {
	
	private static final String TAG = "HttpController Test";
	
	@GetMapping("http/lombok")
	public String lombokTest() {
		Member m = Member.builder().username("sar").password("1234").email("ssar@").build();
		System.out.println(TAG+"getter : "+m.getId());
		m.setId(5000);
		System.out.println(TAG+"getter : "+m.getId());
		return "lombok test완료";
	}
	
	// 인터넷 브라우저 요청은 무조건 get요청밖에 할 수 없다
	// http://localhost:8082/http/get (select)
	@GetMapping("/http/get")
	public String getTest(Member m) {
		System.out.println(TAG+"getter : "+m.getId());
		return "get요청 : "+m.getId()+", "+m.getUsername();//+ m.getId()+", "+m.getUsername();
	}

	// http://localhost:8082/http/post (insert)
	@PostMapping("/http/post") //MessageConverter(스프링부트)가 json형식으로 데이터를 보내면 객체로 받을때 변환해주는 작업을 진행해준다
	public String postTest(@RequestBody Member m) {
		return "post요청 : "+m.getId()+", "+m.getUsername();
	}

	// http://localhost:8082/http/put (update)
	@PutMapping("/http/put")
	public String putTest() {//이것도 정보를 받을때 객체로 받고 json으로 정보를 보낸다면 MessageConverter가 변환을 진행해주는 것이다
		return "put요청";
	}

	// http://localhost:8082/http/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete요청";
	}
}
