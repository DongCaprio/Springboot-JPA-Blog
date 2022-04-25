package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;
@Entity
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private int id;
	 
	@Column(nullable = false, length = 100)
	private String title;
	@Lob
	private String content; //섬모노트 라이브러리<html>태그가 섞여서 디자인이 됨.
	@ColumnDefault("0") // 얘는 숫자이므로 default값안에 " "안에 ' '필요없다
	private int count; //조회수
	
	@ManyToOne //board가 many라는 뜻, user가 one 이라는 뜻 //뭔뜻이냐면 한명의 유저는 여러개의 board를 쓸 수 있다는 것이다
	@JoinColumn(name="userId")
	private User user; //글을 적은사람
	// 중요** :DB는 오브젝트를 저장할 수 없다.FK but 자바는 오브젝트를 저장할 수 있다
	//그래서 얘는 컬럼이 만들어질때 joincolumn의 userid라고 생성된다 그리고 연관관계는 유저객체가 fk로 만들어짐
	
	@CreationTimestamp //현재시간 자동으로 들어감
	private Timestamp createDate;
}
