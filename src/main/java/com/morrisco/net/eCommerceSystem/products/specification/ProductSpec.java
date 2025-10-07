package com.morrisco.net.eCommerceSystem.products.specification;

import com.morrisco.net.eCommerceSystem.products.Category;
import com.morrisco.net.eCommerceSystem.products.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpec {
    //creating specification or a conditions for fetching product by name
    public static Specification<Product> hasName(String name){
        return (Root<Product>root, CriteriaQuery<?>query, CriteriaBuilder cb) ->cb.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Product> hasPriceGreaterThanOrEqualTo(BigDecimal minPrice){
        return (Root<Product>root, CriteriaQuery<?>query, CriteriaBuilder cb) ->cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> hasPriceLessThanOrEqualTo(BigDecimal maxPrice){
        return (Root<Product>root, CriteriaQuery<?>query, CriteriaBuilder cb) ->cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Product> hasCategory(Category category){
        return (Root<Product>root, CriteriaQuery<?>query, CriteriaBuilder cb) ->cb.equal(root.get("category"),category);
    }
}
