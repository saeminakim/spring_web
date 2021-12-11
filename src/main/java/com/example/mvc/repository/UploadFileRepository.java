package com.example.mvc.repository;

import org.springframework.stereotype.Repository;

import com.example.mvc.parameter.UploadFileParameter;

@Repository
public interface UploadFileRepository {

	void save(UploadFileParameter parameter);
}
