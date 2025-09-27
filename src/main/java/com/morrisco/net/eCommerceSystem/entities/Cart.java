package com.morrisco.net.eCommerceSystem.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @OneToMany(mappedBy = "cart",cascade = CascadeType.MERGE,fetch = FetchType.EAGER)//FOR updating the child since the parent already Exists
    @Builder.Default
    @ToString.Exclude
    private Set<CartItem> items = new LinkedHashSet<>();

   public BigDecimal getTotalPrice(){
       return items.stream().map(CartItem::getTotalPrice)
               .reduce(BigDecimal.ZERO,BigDecimal::add);
   }

}
