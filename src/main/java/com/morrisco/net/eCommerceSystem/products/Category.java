package com.morrisco.net.eCommerceSystem.products;

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
    @ToString.Exclude
    private Set<Product> products = new HashSet<>();


//    public void addProduct(Product product){
//        products.add(product);
//        product.setCategory(this);
//    }
}
