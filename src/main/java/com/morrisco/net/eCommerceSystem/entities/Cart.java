package com.morrisco.net.eCommerceSystem.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cart")
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @Column(name = "date_created",insertable = false,updatable = false)
    private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.MERGE)//FOR updating the child since the parent already Exists
    @Builder.Default
    @ToString.Exclude
    private Set<CartItem> cartItems = new LinkedHashSet<>();

}
