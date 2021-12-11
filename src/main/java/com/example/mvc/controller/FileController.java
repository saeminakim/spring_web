package com.example.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import com.example.mvc.parameter.UploadFileParameter;
import com.example.mvc.service.UploadFileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/file")
@Api(tags = "파일 API")
public class FileController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private GlobalConfig config;
	
	@Autowired
	private UploadFileService uploadFileService;
	
	@PostMapping("/save")
	@ApiOperation(value = "업로드", notes = "")
	public BaseResponse<Boolean> save(@RequestParam("uploadFile") MultipartFile multipartFile) {
		
		logger.debug("multipartFile : {}", multipartFile);
		
		if(multipartFile == null || multipartFile.isEmpty()) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL);
		}
		
		// 날짜 폴더를 추가
		String currentData = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		String uploadFilePath = config.getUploadFilePath() + currentData + "/";
		logger.debug("uploadFilePath : {}", uploadFilePath);

		String prefix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1, multipartFile.getOriginalFilename().length());
		String fileName = UUID.randomUUID().toString() + "." + prefix;
		logger.info("fileName : {}", fileName);
		
		// 폴더가 없다면 생성
		File folder = new File(uploadFilePath);
		if(folder.isDirectory()) {
			folder.mkdirs();
		}
		
		String pathName = uploadFilePath + fileName;
		String resourcePathName = config.getUploadResourcePath() + currentData + "/" + fileName;
		File dest = new File(pathName);
		try {
			multipartFile.transferTo(dest);
			// 파일업로드 된 후 DB에 저장
			UploadFileParameter parameter = new UploadFileParameter();
			// 컨텐츠 종류
			parameter.setContentType(multipartFile.getContentType());
			// 원본 파일명
			parameter.setOriginalFileName(multipartFile.getOriginalFilename());
			// 저장 파일명
			parameter.setFileName(fileName);
			// 실제 파일 저장 경로
			parameter.setPathName(pathName);
			// 파일크기
			parameter.setSize((int)multipartFile.getSize());
			// static resource 접근 경로
			parameter.setResourcePathName(resourcePathName);
			
			uploadFileService.save(parameter);
			
		} catch (IllegalStateException | IOException e) {
			logger.error("e",e);
		}
		
		return new BaseResponse<Boolean>(true);
	}

}
