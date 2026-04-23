package com.prowings.productmgmt.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prowings.productmgmt.model.Product;
import com.prowings.productmgmt.service.ProductService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ProductController {

	private ProductService service;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	public ProductController(ProductService service) {
		super();
		this.service = service;
	}

	// ✅ CREATE PRODUCT
	@PostMapping(value = "/products", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> create(@Valid @RequestBody Product product) throws JsonProcessingException {

		String payload = objectMapper.writeValueAsString(product);
		log.info("Request received for create new product : {}", payload);
		Product saved = service.create(product);

		return ResponseEntity.status(201).body(saved);
	}

	// ✅ GET PRODUCT BY ID
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getById(@PathVariable("id") Long id) {

		String transactionId = UUID.randomUUID().toString();
		try {
			MDC.put("txnId", transactionId);

			log.info("Request received for get product of id : {}", id);

			Product product = service.getById(id);

			log.info("...Request completed successfully...");

			return ResponseEntity.ok(product);

		} finally 
		{
			MDC.clear();
		}
	}

	// ✅ GET ALL (with pagination)
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {

		List<Product> products = service.getAll(page, size);

		return ResponseEntity.ok(products);
	}

	// 🔥 SEARCH + FILTER + SORT + PAGINATION
	@GetMapping("/products/search")
	public ResponseEntity<List<Product>> search(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
			@RequestParam(name = "sortDir", defaultValue = "desc") String sortDir) {

		List<Product> products = service.search(name, category, page, size, sortBy, sortDir);

		return ResponseEntity.ok(products);
	}

	// ✅ UPDATE PRODUCT
	@PutMapping("/products/{id}")
	public ResponseEntity<Product> update(@PathVariable("id") Long id, @Valid @RequestBody Product product) {

		Product updated = service.update(id, product);

		return ResponseEntity.ok(updated);
	}

	// ✅ DELETE PRODUCT (Soft Delete)
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

		service.delete(id);

		return ResponseEntity.noContent().build();
	}

}
