package com.cos.blog.test;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//html파일이 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DummyControllerTest {

	@Autowired // 의존성 주입(DI)
	private UserRepository userRepository;
	
	//${id}주소로 파라미터를 전달 받을 수 있다
	// http://localhost:8000/blog/dummy/user/3 이라고치면
	//@PathVariable의 이름과 같다면(여기서는 id) 알아서 매핑해서 값을 넣어준다
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// findById는 리턴값이 Optional 이다 그런 이유는
		// 예로 user/4 를 찾을 때 데이터베이스에서 못찾아오면 user가 null이 되므로
		// 그럼 return null이 리턴이 되므로 Optional로 User객체를 감싸서 가져올테니 
		//null인지 아닌지 판단해서 return하렴 --> 이라는 findById의 큰뜻
		// findById.메소드 엄청 많다... 공부해야될듯
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id : "+id);
			}
		});
		//  요청 : 웹브라우저
		//user 객체 = 자바 오브젝트
		// 변환(웹브라우저가 이해할 수 있는 데이터로 변환) -> JSON
		// 스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
		// 만약 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user오브젝트를 json으로 변환해서 브라우저에게 던져준다
		return user;
	}
	
	// http://localhost:8000/blog/dummy/join(요청)
	// http의 body에 username, password, email 데이터를 가지고 (요청)
	@PostMapping("dummy/join")
	public String join(User user) {
		System.out.println("id : "+user.getId());
		System.out.println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
		System.out.println("role : "+user.getRole());
		System.out.println("createDate : "+user.getCreateDate());
		
		user.setRole(RoleType.USER); //enum을 넣어버림
		userRepository.save(user);
		return "회원가입이 완료되었습니다";
	}
}
