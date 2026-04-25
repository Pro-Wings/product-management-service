package com.prowings.productmgmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prowings.productmgmt.model.Product;
import com.prowings.productmgmt.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ProductController2 {

	private ProductService service;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	public ProductController2(ProductService service) {
		super();
		this.service = service;
	}

	// ✅ CREATE PRODUCT
	@PostMapping(value = "/products2", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> create(HttpEntity<String> httpEntity) throws JsonProcessingException {

		log.info("Request received for create new product");
		
		// 1. Get the headers
	    HttpHeaders headers = httpEntity.getHeaders();
	    String contentType = headers.getFirst(HttpHeaders.CONTENT_TYPE);

	    // 2. Get the body
	    String body = httpEntity.getBody();
	    log.info("Request body : {}", body);
		
	    //3. Deserialize it manually
		Product product = objectMapper.readValue(body, Product.class);
	    
		
		Product saved = service.create(product);

		return ResponseEntity.status(201).body(saved);
	}

}
