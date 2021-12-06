package com.example.jasyptapi.controller;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class StringEncryptionController {
	
	@GetMapping("/encrypt")
	public String encryptString(
			@RequestParam(required=true) String input,
			@RequestParam(required=true) String password,
			@RequestParam(defaultValue="PBEWITHHMACSHA512ANDAES_256") String algorithm,
			@RequestParam(defaultValue="1000") String keyObtentionIterations,
			@RequestParam(defaultValue="SunJCE") String providerName,
			@RequestParam(defaultValue="org.jasypt.salt.RandomSaltGenerator") String saltGeneratorClassName,
			@RequestParam(defaultValue="org.jasypt.iv.RandomIvGenerator") String ivGeneratorClassName,
			@RequestParam(defaultValue="base64") String stringOutputType) {
		try {
			StringEncryptor encryptor = stringEncryptor(
					password, algorithm, keyObtentionIterations, providerName, saltGeneratorClassName, ivGeneratorClassName, stringOutputType
					);
			return encryptor.encrypt(input);
		} catch (EncryptionOperationNotPossibleException e) {
			return "Encryption failed. EncryptionOperationNotPossibleException exception occured.";
		}
	}
	
	@GetMapping("/decrypt")
	public String decryptString(
			@RequestParam(required=true) String input,
			@RequestParam(required=true) String password,
			@RequestParam(defaultValue="PBEWITHHMACSHA512ANDAES_256") String algorithm,
			@RequestParam(defaultValue="1000") String keyObtentionIterations,
			@RequestParam(defaultValue="SunJCE") String providerName,
			@RequestParam(defaultValue="org.jasypt.salt.RandomSaltGenerator") String saltGeneratorClassName,
			@RequestParam(defaultValue="org.jasypt.iv.RandomIvGenerator") String ivGeneratorClassName,
			@RequestParam(defaultValue="base64") String stringOutputType) {
		try {
			StringEncryptor encryptor = stringEncryptor(
					password, algorithm, keyObtentionIterations, providerName, saltGeneratorClassName, ivGeneratorClassName, stringOutputType
					);
			return encryptor.decrypt(input);
		} catch (EncryptionOperationNotPossibleException e) {
			return "Decryption failed. EncryptionOperationNotPossibleException exception occured.";
		}
	}
	
	public static StringEncryptor stringEncryptor(
			String password,
			String algorithm,
			String keyObtentionIterations,
			String providerName,
			String saltGeneratorClassName,
			String ivGeneratorClassName,
			String stringOutputType) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm(algorithm);
        config.setKeyObtentionIterations(keyObtentionIterations);
        config.setPoolSize("1");
        config.setProviderName(providerName);
        config.setSaltGeneratorClassName(saltGeneratorClassName);
        config.setIvGeneratorClassName(ivGeneratorClassName);
        config.setStringOutputType(stringOutputType);
        encryptor.setConfig(config);
        return encryptor;
    }
}
