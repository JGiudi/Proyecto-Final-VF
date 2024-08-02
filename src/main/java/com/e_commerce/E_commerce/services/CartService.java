package com.e_commerce.E_commerce.services;

import com.e_commerce.E_commerce.entities.Cart;
import com.e_commerce.E_commerce.entities.Client;
import com.e_commerce.E_commerce.entities.Product;
import com.e_commerce.E_commerce.repositories.CartRepository;
import com.e_commerce.E_commerce.repositories.ClientRepository;
import com.e_commerce.E_commerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart addProductToCart(Long clientId, Long productId, Integer quantity) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<Cart> carts = cartRepository.findByClientAndDeliveredFalse(client);
        Cart cart;
        if (carts.isEmpty()) {
            cart = new Cart();
            cart.setClient(client);
        } else {
            cart = carts.get(0);
        }

        cart.getProducts().add(product);
        cart.getQuantities().add(quantity);

        return cartRepository.save(cart);
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Optional<Cart> getCartById(Long id) {
        return cartRepository.findById(id);
    }

    public Cart updateCart(Long id, Cart cartDetails) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.setProducts(cartDetails.getProducts());
        cart.setQuantities(cartDetails.getQuantities());
        return cartRepository.save(cart);
    }

    public void removeProductFromCart(Long id, Long productId) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        int index = cart.getProducts().indexOf(product);
        if (index != -1) {
            cart.getProducts().remove(index);
            cart.getQuantities().remove(index);
        }

        cartRepository.save(cart);
    }

    public List<Cart> getCartsByClientId(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return cartRepository.findByClientAndDeliveredFalse(client);
    }
}