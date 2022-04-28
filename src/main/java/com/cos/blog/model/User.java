package com.cos.blog.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//ORM -> Object를 테이블로 매핑해주는 기술 (JPA가 좋은이유!)
@Entity //User 클래스가 자동으로 MySQL에 테이블이 생성이된다
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder 
public class User {
	  
	@Id //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. 	//뭔뜻이나면 오라클이면 시퀀스, mysql이면  Auto_Increment를 따라간다는 뜻
	private int id; //시퀀스
	@Column(nullable = false, length = 30) //null이 될수있고 최대 30글자
	private String username;
	@Column(nullable = false, length = 100) //비밀번호를 해쉬암호화하므로 넉넉하게 준다
	private String password;
	@Column(nullable = false, length = 50)
	private String email;
	
	//@ColumnDefault("'user'") //잘보면 " " 안에 ' ' 있다 이걸 넣어줘야 한다(문자라는것을 알려주기위해)
	//근데위에 어노테이션 안쓰도록 함(enum으로 할것임)
	//DB는 RoleType이라는 게 없다(즉 enum으로 하고 뭘 할건지 알려줘야한다 이번에는 String)
	@Enumerated(EnumType.STRING)
	private RoleType role; //String보다 Enum을 쓰는게 좋다. 타입을 강제하므로 enum으로 //admin, user, manager 권한관리
	
	@CreationTimestamp //시간이 자동입력
	private Timestamp createDate;
}
