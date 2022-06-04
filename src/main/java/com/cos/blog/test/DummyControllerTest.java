package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//html파일이 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DummyControllerTest {

	@Autowired // 의존성 주입(DI)
	private UserRepository userRepository;
	
	//삭제기능
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id){
		//try catch 이유 : 해당 id가 없는것을 요청했을 때 그것에 대한 에러방지용도
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			return "삭제에 실패하셨습니다. 해당id는 DB에 없습니다";
		}
		return "삭제되었습니다.  id : "+id;
	}
	 
	
	// save함수는 id를 전달하지 않으면 insert를 해주고
	// save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	// save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 한다. (update시에는 save메소드를 많이 안쓴다)
	// email, password바꾸기
	@Transactional //이거 넣으면 save함수 주석처리(이거 없으면 save있어야됌), 함수종료시에 자동 commit이 된다
	@PutMapping("/dummy/user/{id}") //요청주소가 같아도 밑에 get과 put은 알아서 구분이 된다 & put은 update이다
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { //json데이터 받으려면 @RequestBody해줘야된다
		System.out.println("id : "+id);																		//Json데이터요청 => 스프링부트(MessageConverter의 Jackson)가 JAVA Object로 변환해서 받아준다
		System.out.println("password : "+requestUser.getPassword());
		System.out.println("email : "+requestUser.getEmail());
		
		//람다식은 findById를 실행해서 못찾을경우 람다식을 실행해라 라는 뜻이다
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		//userRepository.save(user);
		
		//더티 체킹
		return user; 
	}
	
	// http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list(){
		//findall도 이미 만들어져있는 메소드임
		return userRepository.findAll();
	}
	
	//한 페이지당 2건의 데이터를 리턴받아 볼 예정 
	//스프링부트는 이미 페이징을 할 수 있는 메소드가 구현되어 있다 @PageableDefault 페이징은 0부터 시작 (url에 ?page=0)
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size = 2,sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable); //Page<User>를 리턴하면 페이지에 대한 정보값까지 다 알려줌
		List<User> users = pagingUser.getContent(); //순수하게 List에 대한 정보만 얻고싶을때 이것을 리턴한다 
		return pagingUser;
	}
	
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
		userRepository.save(user); //insert시 save함수 사용!
		//insert하려고 save할때는 @Transactional 안붙인다.
		return "회원가입이 완료되었습니다";
	}
}
