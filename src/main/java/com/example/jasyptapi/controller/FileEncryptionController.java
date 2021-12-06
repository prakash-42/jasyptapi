package com.example.jasyptapi.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileEncryptionController {
	
	@RequestMapping(value = "/encrypt", method = RequestMethod.POST)
	public String encryptFile(
			@RequestParam(required=true) MultipartFile inputFile,
			@RequestParam(required=true) String password,
			@RequestParam(defaultValue="PBEWITHHMACSHA512ANDAES_256") String algorithm,
			@RequestParam(defaultValue="1000") String keyObtentionIterations,
			@RequestParam(defaultValue="SunJCE") String providerName,
			@RequestParam(defaultValue="org.jasypt.salt.RandomSaltGenerator") String saltGeneratorClassName,
			@RequestParam(defaultValue="org.jasypt.iv.RandomIvGenerator") String ivGeneratorClassName,
			@RequestParam(defaultValue="base64") String stringOutputType) {
		
		String content = "";
		try {
			content = new String(inputFile.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			return "Couldn't read file";
		}
		return content;
	}
	
	@RequestMapping(value = "/decrypt", method = RequestMethod.POST)
	public String decryptFile(
			@RequestParam(required=true) MultipartFile inputFile,
			@RequestParam(required=true) String password,
			@RequestParam(defaultValue="PBEWITHHMACSHA512ANDAES_256") String algorithm,
			@RequestParam(defaultValue="1000") String keyObtentionIterations,
			@RequestParam(defaultValue="SunJCE") String providerName,
			@RequestParam(defaultValue="org.jasypt.salt.RandomSaltGenerator") String saltGeneratorClassName,
			@RequestParam(defaultValue="org.jasypt.iv.RandomIvGenerator") String ivGeneratorClassName,
			@RequestParam(defaultValue="base64") String stringOutputType) {
		
		String content = "";
		try {
			content = new String(inputFile.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			return "Couldn't read file";
		}
		return content;
	}
	
}
