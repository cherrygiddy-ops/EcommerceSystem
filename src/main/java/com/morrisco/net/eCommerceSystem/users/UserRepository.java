package com.morrisco.net.eCommerceSystem.users;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByPassword(String password);

    Optional<User> findByEmail(@NotNull(message = "email not null") String email);

//    //@Query("select u.email from User u where u.email = ?1")
//    @EntityGraph(attributePaths = {"tags","addresses"}) //changing the relationship attributes btwn tags and user to be ager loading
//    Optional<User>findByEmail(String email);
//
//    @EntityGraph(attributePaths = "addresses")
//    @Query("select u from User u")
//    List<User>findAllWithAddresses();
//
//    @Query("select u.id as id,u.email as email from User u where u.profile.loyaltyPoints > :loyaltyPoints order by u.email")
//    List<UserSummary> findLoyalUsers(@Param("loyaltyPoints") int loyaltyPoints);
}