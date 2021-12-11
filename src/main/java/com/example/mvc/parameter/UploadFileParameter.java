package com.example.mvc.parameter;

import lombok.Data;

@Data
public class UploadFileParameter {

	private String pathName;
	private String fileName;
	private String originalFileName;
	private int size;
	private String contentType;
	private String resourcePathName;
}
