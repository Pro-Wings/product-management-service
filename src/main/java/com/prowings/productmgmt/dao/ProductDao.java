package com.prowings.productmgmt.dao;

import java.util.List;

import com.prowings.productmgmt.model.Product;

public interface ProductDao {

	void save(Product product);

	Product getById(Long id);

	List<Product> getAll(int page, int size);

//	List<Product> search(String name, String category, int page, int size, String sortBy, String sortDir);

	void update(Product product);

	void delete(Long id);

}
