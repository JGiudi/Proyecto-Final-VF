package com.e_commerce.E_commerce.repositories;

import com.e_commerce.E_commerce.entities.Cart;
import com.e_commerce.E_commerce.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByClientAndDeliveredFalse(Client client);
    Cart findByClientIdAndDeliveredFalse(Long clientId);
}
