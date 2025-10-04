package com.morrisco.net.eCommerceSystem.repositories;

import com.morrisco.net.eCommerceSystem.entities.Order;
import com.morrisco.net.eCommerceSystem.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @EntityGraph(attributePaths = "items.product")
   //@Query("SELECT o FROM orders o WHERE o.customer = :customer")
    List<Order> findByCustomer(@Param("customer") User customer);



    //List<Order> findAllByCustomer(User customer);
}
