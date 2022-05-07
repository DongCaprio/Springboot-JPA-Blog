package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;

//DAO라고 생각하면된다
//자동으로 bean등록이된다
//@Repository 생략가능하다 japrepository만 extends하면
public interface UserRepository extends JpaRepository<User, Integer>{
	//jparepository는 user를 관리하는 repository이다 
	//그리고 user테이블의 primary key는 integer야! 라는 뜻이다 (뒤에 꺽쇠)
	
	// SELECT * FROM user WHERE username = 1?;
	// 네이밍규칙으로 인해 위의 쿼리가 들어감
	Optional<User> findByUsername(String username);
	
	
}
//JPA Naming 전략
	// 이름을 이렇게하면 자동으로 SELECT * FROM user WHERE username = ? AND password = ?
	//자동으로 이 쿼리가 실행됌(메소드명만 저렇게하면 JPA 자동기능)
//	User findByUsernameAndPassword(String username, String password);
	
	//위에것을 수동으로 만들자면 이렇게 만들면 된다.
//	@Query(value="SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//	User login(String username, String password);