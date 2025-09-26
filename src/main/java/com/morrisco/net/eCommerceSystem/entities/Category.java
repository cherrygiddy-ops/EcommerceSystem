package com.morrisco.net.eCommerceSystem.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "categories")
@Entity
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Byte id;

    @Column(nullable = false,name = "name")
    private String name;

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private Set<Product> products = new HashSet<>();

    public Category(byte categoryId) {
        this.id =categoryId;
    }

//    public void addProduct(Product product){
//        products.add(product);
//        product.setCategory(this);
//    }
}
