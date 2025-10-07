package com.morrisco.net.eCommerceSystem.products;



import java.math.BigDecimal;
import java.util.List;

public interface ProductCriteriaRepository {
    List<Product> findProductsByCriteria(String name , BigDecimal minPrice, BigDecimal maxPrice, Category category);
}
