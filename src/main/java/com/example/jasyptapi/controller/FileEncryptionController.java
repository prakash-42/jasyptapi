package com.example.jasyptapi.controller;

import java.io.IOException;

import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.intf.service.FileEncryptorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileEncryptionController {
	
	@PostMapping("/encrypt")
	public ResponseEntity<String> encryptFile(
			@RequestParam(required=true) MultipartFile inputFile,
			@RequestParam(required=true) String password,
			@RequestParam(defaultValue="PBEWITHHMACSHA512ANDAES_256") String algorithm,
			@RequestParam(defaultValue="1000") String keyObtentionIterations,
			@RequestParam(defaultValue="SunJCE") String providerName,
			@RequestParam(defaultValue="org.jasypt.salt.RandomSaltGenerator") String saltGeneratorClassName,
			@RequestParam(defaultValue="org.jasypt.iv.RandomIvGenerator") String ivGeneratorClassName,
			@RequestParam(defaultValue="base64") String stringOutputType,
			@RequestParam(defaultValue="ENC(") String encryptedPrefix,
			@RequestParam(defaultValue=")") String encryptedSuffix,
			@RequestParam(defaultValue="DEC(") String decryptedPrefix,
			@RequestParam(defaultValue=")") String decryptedSuffix) {
		
		String content = "";
		try {
			content = new String(inputFile.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("[ERROR] Couldn't read file", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		SimpleStringPBEConfig config = getPBEConfig(
				password, algorithm, keyObtentionIterations, providerName, saltGeneratorClassName, ivGeneratorClassName, stringOutputType
				);
		FileEncryptorService fileEncryptorService = new FileEncryptorService();
		try {
			return new ResponseEntity<>(
					fileEncryptorService.encrypt(content, config, encryptedPrefix, encryptedSuffix, decryptedPrefix, decryptedSuffix, false),
					HttpStatus.OK);
		} catch (EncryptionOperationNotPossibleException e) {
			return new ResponseEntity<>("[ERROR] Encryption failed. EncryptionOperationNotPossibleException exception occured.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/decrypt")
	public ResponseEntity<String> decryptFile(
			@RequestParam(required=true) MultipartFile inputFile,
			@RequestParam(required=true) String password,
			@RequestParam(defaultValue="PBEWITHHMACSHA512ANDAES_256") String algorithm,
			@RequestParam(defaultValue="1000") String keyObtentionIterations,
			@RequestParam(defaultValue="SunJCE") String providerName,
			@RequestParam(defaultValue="org.jasypt.salt.RandomSaltGenerator") String saltGeneratorClassName,
			@RequestParam(defaultValue="org.jasypt.iv.RandomIvGenerator") String ivGeneratorClassName,
			@RequestParam(defaultValue="base64") String stringOutputType,
			@RequestParam(defaultValue="ENC(") String encryptedPrefix,
			@RequestParam(defaultValue=")") String encryptedSuffix,
			@RequestParam(defaultValue="DEC(") String decryptedPrefix,
			@RequestParam(defaultValue=")") String decryptedSuffix) {
		
		String content = "";
		try {
			content = new String(inputFile.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("[ERROR] Couldn't read file", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		SimpleStringPBEConfig config = getPBEConfig(
				password, algorithm, keyObtentionIterations, providerName, saltGeneratorClassName, ivGeneratorClassName, stringOutputType
				);
		FileEncryptorService fileEncryptorService = new FileEncryptorService();
		try {
			return new ResponseEntity<>(
					fileEncryptorService.decrypt(content, config, encryptedPrefix, encryptedSuffix, decryptedPrefix, decryptedSuffix, false),
					HttpStatus.OK);
		} catch (EncryptionOperationNotPossibleException e) {
			return new ResponseEntity<>("[ERROR] Encryption failed. EncryptionOperationNotPossibleException exception occured.", HttpStatus.BAD_REQUEST);
		}
	}
	
	public static SimpleStringPBEConfig getPBEConfig(
			String password,
			String algorithm,
			String keyObtentionIterations,
			String providerName,
			String saltGeneratorClassName,
			String ivGeneratorClassName,
			String stringOutputType) {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm(algorithm);
        config.setKeyObtentionIterations(keyObtentionIterations);
        config.setProviderName(providerName);
        config.setSaltGeneratorClassName(saltGeneratorClassName);
        config.setIvGeneratorClassName(ivGeneratorClassName);
        config.setStringOutputType(stringOutputType);
        return config;
    }
	
}
