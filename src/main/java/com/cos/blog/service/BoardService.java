package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private ReplyRepository replyRepository;

	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 상세보기 실패 : 아이드를 찾을 수 없습니다");
		});

	}

	@Transactional
	public void 글쓰기(Board board, User user) { // title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

	// page를 반환해야 첫번쨰 페이지 마지막 페이지 등등
	// 페이지에 대한 정보를 얻을 수 있기 때문에 마지막페이지에 대한 정보를 받는다.
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	@Transactional
	public void 글삭제하기(int id) {
		System.out.println("글삭제하기" + id);
		boardRepository.deleteById(id);
	}

	@Transactional
	public void 글수정하기(int id, Board reqeusetBoard) {
		Board board = boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 찾기 실패 : 아이드를 찾을 수 없습니다");
		}); // 영속화 완료
		board.setTitle(reqeusetBoard.getTitle());
		board.setContent(reqeusetBoard.getContent());
		// 해당 함수로 종료시에 (Service가 종료될 때) 트랜잭션이 종료됩니다. 이때 더티체킹 - 자동 업데이트됨 db flush
	}

	@Transactional
	public void 댓글쓰기(User user, int boardId, Reply requestReply) {

		Board board = boardRepository.findById(boardId).orElseThrow(() -> {
			return new IllegalArgumentException("댓글쓰기실패 : 게시글 id를 찾을 수 없습니다.");
		}); // 영속화 완료;
		requestReply.setUser(user);
		requestReply.setBoard(board);
		replyRepository.save(requestReply);
	}

}
