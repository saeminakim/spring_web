package com.example.mvc.domain;

import lombok.Data;

@Data
public class UploadFile {
	private int uploadFileSeq;
	private String pathName;
	private String fileName;
	private String originalFileName;
	private String size;
	private String contentType;
	private String resourcePathName;
}
