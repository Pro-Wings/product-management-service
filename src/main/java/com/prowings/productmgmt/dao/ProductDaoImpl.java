package com.prowings.productmgmt.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.prowings.productmgmt.model.Product;

@Repository
public class ProductDaoImpl implements ProductDao{

    @Autowired
    private SessionFactory sessionFactory;
	
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    
    // ✅ CREATE
    @Override
    public void save(Product product) {
        getSession().save(product);
    }
    
    // ✅ GET BY ID
    @Override
    public Product getById(Long id) {
        return getSession().get(Product.class, id);
    }

    // ✅ GET ALL (with pagination)
    @Override
    public List<Product> getAll(int page, int size) {

        Query<Product> query = getSession()
                .createQuery("FROM Product WHERE isActive = true", Product.class);

        query.setFirstResult(page * size);
        query.setMaxResults(size);

        return query.list();
    }

    // 🔥 SEARCH + SORT + PAGINATION (CORE)
//    @Override
//    public List<Product> search(String name, String category,
//                               int page, int size,
//                               String sortBy, String sortDir) {
//
//        StringBuilder hql = new StringBuilder("FROM Product p WHERE p.isActive = true ");
//
//        // 🔹 Dynamic filters
//        if (name != null && !name.isBlank()) {
//            hql.append("AND lower(p.name) LIKE :name ");
//        }
//
//        if (category != null && !category.isBlank()) {
//            hql.append("AND lower(p.category) = :category ");
//        }
//
//        // 🔹 Sorting
//        if (sortBy != null && !sortBy.isBlank()) {
//            hql.append("ORDER BY p.").append(sortBy).append(" ");
//            hql.append("asc".equalsIgnoreCase(sortDir) ? "ASC " : "DESC ");
//        }
//
//        Query<Product> query = getSession().createQuery(hql.toString(), Product.class);
//
//        // 🔹 Set parameters
//        if (name != null && !name.isBlank()) {
//            query.setParameter("name", "%" + name.toLowerCase() + "%");
//        }
//
//        if (category != null && !category.isBlank()) {
//            query.setParameter("category", category.toLowerCase());
//        }
//
//        // 🔹 Pagination
//        query.setFirstResult(page * size);
//        query.setMaxResults(size);
//
//        return query.list();
//    }

    // ✅ UPDATE
    @Override
    public void update(Product product) {
        getSession().update(product);
    }

    // ✅ SOFT DELETE
    @Override
    public void delete(Long id) {

        Product product = getById(id);

        if (product != null) {
            product.setIsActive(false);
            getSession().update(product);
        }
    }
    
}
