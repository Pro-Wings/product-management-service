package com.prowings.productmgmt.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prowings.productmgmt.dao.ProductDao;
import com.prowings.productmgmt.exception.ProductNotFoundException;
import com.prowings.productmgmt.exception.ProductValidationException;
import com.prowings.productmgmt.model.Product;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {

	private ProductDao dao;

	@Autowired
	public ProductServiceImpl(ProductDao dao) {
		super();
		this.dao = dao;
	}

	// ✅ CREATE
	@Override
	public Product create(Product product) {

		validateProduct(product);

		dao.save(product);
		return product;
	}

	// ✅ GET BY ID
	@Override
	public Product getById(Long id) {

		log.info("---inside getById() of service class ---");

		log.info("---fetching product of given id : {} from database ---",id);
		
		Product product = dao.getById(id);
		

		if (product == null || Boolean.FALSE.equals(product.getIsActive())) {
			log.error("!!! Product of given id: {} not exist in DB !!!");
			
			throw new ProductNotFoundException("Product not found with id: " + id);
		}

		log.info("---fetched product of given id : {} from database successfully---",id);
		
		return product;
	}

	// ✅ GET ALL
	@Override
	public List<Product> getAll(int page, int size) {
		return dao.getAll(page, size);
	}

//	// ✅ SEARCH
	@Override
	public List<Product> search(String name, String category, int page, int size, String sortBy, String sortDir) {

		return dao.search(name, category, page, size, sortBy, sortDir);
	}

	// ✅ UPDATE
	@Override
	public Product update(Long id, Product updatedProduct) {

		Product existing = dao.getById(id);

		if (existing == null || Boolean.FALSE.equals(existing.getIsActive())) {
			throw new ProductNotFoundException("Product not found with id: " + id);
		}

		validateProduct(updatedProduct);

		// 🔥 Update only allowed fields
		existing.setName(updatedProduct.getName());
		existing.setDescription(updatedProduct.getDescription());
		existing.setPrice(updatedProduct.getPrice());
		existing.setStock(updatedProduct.getStock());
		existing.setCategory(updatedProduct.getCategory());
		existing.setBrand(updatedProduct.getBrand());

		dao.update(existing);

		return existing;
	}

	// ✅ DELETE (Soft Delete)
	@Override
	public void delete(Long id) {

		Product product = dao.getById(id);

		if (product == null || Boolean.FALSE.equals(product.getIsActive())) {
			throw new ProductNotFoundException("Product not found with id: " + id);
		}

		dao.delete(id);
	}

	// 🔥 CENTRALIZED BUSINESS VALIDATION
	private void validateProduct(Product product) {

		// Price validation
		if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new ProductValidationException("Price must be greater than 0");
		}

		// Stock validation
		if (product.getStock() == null || product.getStock() < 0) {
			throw new ProductValidationException("Stock cannot be negative");
		}
	}

}
