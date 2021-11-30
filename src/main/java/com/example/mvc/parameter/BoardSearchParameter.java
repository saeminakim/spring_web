package com.example.mvc.parameter;

import java.util.List;

import com.example.mvc.domain.BoardType;

import lombok.Data;

/**
 * 게시물 검색 파라미터  
 * @author Lily
 *
 */
@Data
public class BoardSearchParameter {

	private String keyword;
	private List<BoardType> boardTypes;
	
	public BoardSearchParameter() {
		
	}

}
