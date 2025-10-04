package com.morrisco.net.eCommerceSystem.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "total_price")
    private BigDecimal totalPrice;


    @JoinColumn(name = "order_id")
    @ManyToOne()
    @ToString.Exclude
    private Order order;

    @JoinColumn(name = "product_id")
    @ManyToOne()
    @ToString.Exclude
    private Product product;

    public OrderItem(Order order, Product product, BigDecimal quantity) {
        this.unitPrice = product.getPrice();
        this.quantity = quantity;
        this.totalPrice = product.getPrice().multiply(quantity);
        this.order = order;
        this.product = product;
    }
}
