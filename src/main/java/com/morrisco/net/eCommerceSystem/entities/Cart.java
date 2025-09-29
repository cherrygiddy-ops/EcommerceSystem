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

    @OneToMany(mappedBy = "cart",cascade = CascadeType.MERGE,fetch = FetchType.EAGER,orphanRemoval = true)//FOR updating the child since the parent already Exists
    //orphanRemoval = true -> means that if a child become null removes it
    @Builder.Default
    @ToString.Exclude
    private Set<CartItem> items = new LinkedHashSet<>();

   public BigDecimal getTotalPrice(){
       return items.stream().map(CartItem::getTotalPrice)
               .reduce(BigDecimal.ZERO,BigDecimal::add);
   }

   public CartItem addToCart (Product product){
       var cartItem = filterItem(product.getId());
       if (cartItem !=null)
           cartItem .setQuantity(cartItem.getQuantity() +1);
       else {
           cartItem = new CartItem();
           cartItem.setProduct(product);
           cartItem.setQuantity(1);
           cartItem.setCart(this);

           items.add(cartItem);

       }
       return cartItem;
   }
   public CartItem filterItem(Long productID){
           return items.stream().filter(p -> p.getProduct().getId().equals(productID)).findFirst().orElse(null);
       }
  public void deleteItem(Long productId){
       var cartItem =filterItem(productId);
       if (cartItem !=null) {
           items.remove(cartItem);
           cartItem.setCart(null);
       }

  }
}
