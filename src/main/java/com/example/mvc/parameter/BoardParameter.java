package com.example.mvc.parameter;

import com.example.mvc.domain.BoardType;

import lombok.Data;

@Data
public class BoardParameter {
	
	private int boardSeq;
	private BoardType boardType;
	private String title;
	private String contents;
	private boolean delYn;
	
	public BoardParameter() {
		
	}
	
	public BoardParameter(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}

}
