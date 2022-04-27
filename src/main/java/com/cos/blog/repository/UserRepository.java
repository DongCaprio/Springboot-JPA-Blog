package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

//DAO라고 생각하면된다
//자동으로 bean등록이된다
//@Repository 생략가능하다 japrepository만 extends하면
public interface UserRepository extends JpaRepository<User, Integer>{
	//jparepository는 user를 관리하는 repository이다 
	//그리고 user테이블의 primary key는 integer야! 라는 뜻이다 (뒤에 꺽쇠)
	
}
