package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;

//DAO라고 생각하면된다
//자동으로 bean등록이된다
//@Repository 생략가능하다 japrepository만 extends하면
public interface BoardRepository extends JpaRepository<Board, Integer>{

}
