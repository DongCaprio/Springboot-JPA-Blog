package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private int id;
	 
	@Column(nullable = false, length = 100)
	private String title;

	@Lob //대용량 데이터 필요할때 쓴다
	private String content; //섬머노트 라이브러리<html>태그가 섞여서 디자인이 됨.

	//@ColumnDefault("0") // 얘는 숫자이므로 default값안에 " "안에 ' '필요없다
	
	private int count; //조회수
	
	@ManyToOne(fetch = FetchType.EAGER)//fetchtype.eager은 기본전략임 //board가 many라는 뜻, user가 one 이라는 뜻 //뭔뜻이냐면 한명의 유저는 여러개의 board를 쓸 수 있다는 것이다
	@JoinColumn(name="userId")
	private User user; //글을 적은사람
	// 중요** :DB는 오브젝트를 저장할 수 없다.FK but 자바는 오브젝트를 저장할 수 있다
	//그래서 얘는 컬럼이 만들어질때 joincolumn의 userid라고 생성된다 그리고 연관관계는 유저객체가 fk로 만들어짐
	
	 // 어노테이션 joincolum필요없음!!!!! DB에서 하나의 칸안에는 하나의 값만 들어가야하므로
	//mappedBy 연관관계의 주인이 아니다(난 FK가 아니에요) DB에 컬럼을 만들지 마세요 //mappedBy에서 board는 Reply.java의 필드값을 적어주면된다.(name값아님! 자바필드값 board)
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) //cascade는 board삭제할때 댓글까지 지워버리겠다는 뜻
	@JsonIgnoreProperties({"board"}) //jpa무한참조방지
	@OrderBy("id desc")
	 private List<Reply> replys;
	  
	@CreationTimestamp //현재시간 자동으로 들어감
	private Timestamp createDate;
}
