package com.prowings.productmgmt.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prowings.productmgmt.exception.ProductNotFoundException;
import com.prowings.productmgmt.exception.ProductValidationException;
import com.prowings.productmgmt.model.CustomErrorResponse;
import com.prowings.productmgmt.model.Product;
import com.prowings.productmgmt.service.ProductService;

import jakarta.validation.Valid;

@RestController
public class ProductController {

	private ProductService service;

	@Autowired
	public ProductController(ProductService service) {
		super();
		this.service = service;
	}

	// ✅ CREATE PRODUCT
	@PostMapping(value = "/products", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
		}

		Product saved = service.create(product);

		return ResponseEntity.status(201).body(saved);
	}

	// ✅ GET PRODUCT BY ID
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getById(@PathVariable("id") Long id) {

		Product product = service.getById(id);

		return ResponseEntity.ok(product);
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
	public ResponseEntity<List<Product>> search(@RequestParam(name = "name",required = false) String name,
			@RequestParam(name = "category",required = false) String category, 
			@RequestParam(name = "page",defaultValue = "0") int page,
			@RequestParam(name = "size",defaultValue = "5") int size, 
			@RequestParam(name = "sortBy",defaultValue = "createdAt") String sortBy,
			@RequestParam(name = "sortDir",defaultValue = "desc") String sortDir) {

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

	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<CustomErrorResponse> handleRuntimeException(Exception e)
	{
		CustomErrorResponse errorResponse = new CustomErrorResponse(404, e.getMessage(), LocalDateTime.now());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ProductValidationException.class)
	public ResponseEntity<CustomErrorResponse> handleValidationException(ProductValidationException e)
	{
		CustomErrorResponse errorResponse = new CustomErrorResponse(400, e.getMessage(), LocalDateTime.now());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
}
