package com.morrisco.net.eCommerceSystem.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.action.internal.OrphanRemovalAction;

import java.math.BigDecimal;

@Entity(name = "order_items")
@Getter
@Setter

public class OrderItems {

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
    private Order order;

    @JoinColumn(name = "product_id")
    @ManyToOne()
    private Product product;

}
