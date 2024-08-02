package com.e_commerce.E_commerce.repositories;

import com.e_commerce.E_commerce.entities.Cart;
import com.e_commerce.E_commerce.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByClientAndDeliveredFalse(Client client);
    Cart findByClientIdAndDeliveredFalse(Long clientId);
    Optional<Cart> findByClient_IdAndDeliveredFalse(Long clientId);
    Optional<Cart> findFirstByClient_IdAndDeliveredFalseOrderByCreatedAtDesc(Long clientId);
    void deleteById(Long id);
}
