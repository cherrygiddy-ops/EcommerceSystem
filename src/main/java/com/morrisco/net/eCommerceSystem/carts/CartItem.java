package com.morrisco.net.eCommerceSystem.carts;

import com.morrisco.net.eCommerceSystem.products.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "cart_item")
@Entity
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private BigDecimal quantity;

    public  BigDecimal getTotalPrice(){
        return product.getPrice().multiply(quantity);
    }
}
