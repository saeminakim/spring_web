package com.example.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mvc.domain.Board;
import com.example.mvc.service.BoardService;

/**
 * 게시판 컨트롤러
 * @author Lily
 *
 */
@RestController
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	/**
	 * 목록 리턴
	 * @return
	 */
	@GetMapping
	public List<Board> getList() {
		return boardService.getList();
	}
	
	/**
	 * 상세 정보 리턴
	 * @param boardSeq
	 * @return
	 */
	@GetMapping("/{boardSeq}")
	public Board get(@PathVariable int boardSeq) {
		return boardService.get(boardSeq);
	}
	
	/**
	 * 등록/수정 처리
	 * @param board
	 */
	@GetMapping("/save")
	public int save(Board board) {
		boardService.save(board);
		return board.getBoardSeq();
	}
	
	/**
	 * 업데이트 처리
	 * @param board
	 */
	@GetMapping("/delete/{boardSeq}")
	public void update(@PathVariable Board board) {
		boardService.update(board);
	}
	
	/**
	 * 삭제 처리
	 * @param boardSeq
	 */
	public void delete(int boardSeq) {
		boardService.delete(boardSeq);
	}
	
}
