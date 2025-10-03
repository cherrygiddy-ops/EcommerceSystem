package com.morrisco.net.eCommerceSystem.entities;

import com.morrisco.net.eCommerceSystem.dtos.CartDto;
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
    private OrderStatus status;

    @Column(name = "created_at",insertable = false,updatable = false)
    private LocalDate dateCreated;

    @Column(name = "total_price")
    private BigDecimal price;


    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private User customer;

    @OneToMany(mappedBy = "order",orphanRemoval = true,cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private Set<OrderItems> orderedItems = new LinkedHashSet<>();


    public static Order createOrderFromCart(Cart cart,User customer){
        var order= new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PENDING);
        order.setPrice(cart.getTotalPrice());

        cart.getItems().forEach(item->{
            var orederItem = new OrderItems(
                    order,
                    item.getProduct(),
                    item.getQuantity());
            order.orderedItems.add(orederItem);
        });

        return order;
    }


}
