package com.morrisco.net.eCommerceSystem.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor

public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "created_at",insertable = false,updatable = false)
    private LocalDate createdAt;

    @Column(name = "total_price")
    private BigDecimal totalPrice;


    @ManyToOne()
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    private User customer;

    @OneToMany(mappedBy = "order",orphanRemoval = true,cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @ToString.Exclude
    private Set<OrderItem> items = new LinkedHashSet<>();


    public static Order createOrderFromCart(Cart cart,User customer){
        var order= new Order();
        order.setCustomer(customer);
        order.setStatus(PaymentStatus.PENDING);
        order.setTotalPrice(cart.getTotalPrice());

        cart.getItems().forEach(item->{
            var orderItem = new OrderItem(
                    order,
                    item.getProduct(),
                    item.getQuantity());
            order.items.add(orderItem);
        });

        return order;
    }


}
