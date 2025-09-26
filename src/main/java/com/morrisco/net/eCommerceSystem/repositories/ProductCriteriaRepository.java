package com.morrisco.net.eCommerceSystem.repositories;



import com.morrisco.net.eCommerceSystem.entities.Category;
import com.morrisco.net.eCommerceSystem.entities.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductCriteriaRepository {
    List<Product> findProductsByCriteria(String name , BigDecimal minPrice, BigDecimal maxPrice, Category category);
}
