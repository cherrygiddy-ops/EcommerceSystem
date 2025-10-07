package com.morrisco.net.eCommerceSystem.products;

import com.morrisco.net.eCommerceSystem.entities.CartItem;
import com.morrisco.net.eCommerceSystem.entities.OrderItem;
import com.morrisco.net.eCommerceSystem.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
@Entity
@ToString
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name ="description")
    private String description;

    @ManyToOne(cascade = CascadeType.PERSIST)//Eager Loading
    @JoinColumn(name = "category_id") //this is the foreignKey Column
    @ToString.Exclude
    private Category category;

    @ManyToMany(mappedBy = "wishList") //telling to hibernate who is the owner of the relationship
    @ToString.Exclude//EXCLUDING TAGS from being converted to Tostring because it can form a cycle
    private Set<User> users = new HashSet<>();//user can not have duplicate products

    @OneToMany(mappedBy = "product")
    @Builder.Default
    @ToString.Exclude
    private Set<CartItem> cartItems = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product",orphanRemoval = true,cascade = CascadeType.MERGE)
    private Set<OrderItem> orderItems = new LinkedHashSet<>();

}
