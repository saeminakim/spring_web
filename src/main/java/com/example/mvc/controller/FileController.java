package com.example.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.configuration.GlobalConfig;
import com.example.configuration.exception.BaseException;
import com.example.configuration.http.BaseResponse;
import com.example.configuration.http.BaseResponseCode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/file")
@Api(tags = "파일 API")
public class FileController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private GlobalConfig config;
	
	@PostMapping("/save")
	@ApiOperation(value = "업로드", notes = "")
	public BaseResponse<Boolean> save(@RequestParam("uploadFile") MultipartFile multipartFile) {
		
		logger.debug("multipartFile : {}", multipartFile);
		
		if(multipartFile == null || multipartFile.isEmpty()) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL);
		}
		
		String uploadFilePath = config.getUploadFilePath();
		logger.debug("uploadFilePath : {}", uploadFilePath);
		
		if(config.isProd()) {
			logger.debug("isProd calendar : {}", Calendar.getInstance());
		}
		
		String prefix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1, multipartFile.getOriginalFilename().length());
		String fileName = UUID.randomUUID().toString() + "." + prefix;
		logger.info("fileName : {}", fileName);
		
		// 폴더가 없다면 생성
		File folder = new File(uploadFilePath);
		if(folder.isDirectory()) {
			folder.mkdirs();
		}
		
		String pathName = uploadFilePath + fileName;
		File dest = new File(pathName);
		try {
			multipartFile.transferTo(dest);
		} catch (IllegalStateException | IOException e) {
			logger.error("e",e);
		}
		
		return new BaseResponse<Boolean>(true);
	}

}
