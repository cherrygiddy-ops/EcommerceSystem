package com.morrisco.net.eCommerceSystem.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor //jpa and hibernate expect default constructor  but here java initial this fields  so we need to apply another annotaion call
                    //@NoArgsConstructor to return back to default constructor
@NoArgsConstructor
@Builder //used for building object step by step
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//used to specify how ids are generated i.e primary keys
    @Column(name = "id")
    private Long id;

    @Column(nullable = false,name = "name")
    private String name;

    @Column(nullable = false,name = "email")
    private String email;

    @Column(nullable = false,name = "password")
    private String password;

    @OneToMany(mappedBy = "user" , cascade = { CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval = true)//telling hibernate who is the owner of the relationship
    @Builder.Default //telling builder annotation to include new ArrayList<>(); when building an object
    private List<Addresses> addresses = new ArrayList<>();

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

//    public void addAddress(Addresses address){
//        addresses.add(address);
//        address.setUser(this);
//    }
//
//    public void removeAddress(Addresses address){
//        addresses.remove(address);
//        address.setUser(null);
//    }


    @ManyToMany
    @JoinTable(
            name = "user_tags",
            joinColumns =@JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id")
    )
    @Builder.Default
    private Set<Tag>tags = new HashSet<>();//user can not have duplicate tags

    public void addTag(String tagName){
        var tag = new Tag(tagName);
        tags.add(tag);
    }

//    @OneToOne(mappedBy = "user",cascade = CascadeType.REMOVE)
//    private Profile profile;

    @ManyToMany()
    @JoinTable(
            name = "wishlist",
            joinColumns =@JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id")
    )
    @Builder.Default
    private Set<Product> wishList = new HashSet<>();//user can not have duplicate tags

    public void addProduct(Product product){
       wishList.add(product);
    }
    public void addProducts(List<Product> products){
        wishList.addAll(products);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "email = " + email +", " +
                "password ="+ password +")" ;
    }
}
