package com.prowings.productmgmt.service;

import java.util.List;

import com.prowings.productmgmt.model.Product;

public interface ProductService {
	
    Product create(Product product);

    Product getById(Long id);

    List<Product> getAll(int page, int size);

//    List<Product> search(String name, String category,
//                         int page, int size,
//                         String sortBy, String sortDir);

    Product update(Long id, Product product);

    void delete(Long id);

}
