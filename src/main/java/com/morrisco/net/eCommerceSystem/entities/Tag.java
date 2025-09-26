package com.morrisco.net.eCommerceSystem.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "tags")
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = false,name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags") //telling to hibernate who is the owner of the relationship
    @ToString.Exclude//EXCLUDING TAGS from being converted to Tostring because it can form a cycle
    private Set<User> tags = new HashSet<>();//user can not have duplicate tags

    public Tag(String name) {
        this.name = name;
    }
}
