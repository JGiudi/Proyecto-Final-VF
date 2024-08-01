package com.e_commerce.E_commerce.controllers;

import com.e_commerce.E_commerce.entities.Cart;
import com.e_commerce.E_commerce.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carts")
@Tag(name = "Carts path", description = "CRUD operations for e-commerce")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{clientId}/{productId}/{quantity}")
    @Operation(summary = "Create one cart", description = "You need to pass an object with the data for the cart, customer, product, and quantity, and it returns the cart")
    public ResponseEntity<Cart> addProductToCart(
            @PathVariable("clientId") Long clientId,
            @PathVariable("productId") Long productId,
            @PathVariable("quantity") Integer quantity) {
        Cart cart = cartService.addProductToCart(clientId, productId, quantity);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/getAll")
    @Operation(summary = "Get one cart", description = "This return all carts")
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get one cart by ID", description = "You need to pass the ID, and it will return the cart with that ID. If there is no cart with that ID, it will return the object")
    public ResponseEntity<Cart> getCartById(@PathVariable("id") Long id) {
        Optional<Cart> cart = cartService.getCartById(id);
        return cart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update cart", description = "You need to pass an object with the data for the cart, product, and quantity, and it returns the cart, this return the object")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Devuelve correctamente la mascota encontrada"),
            @ApiResponse( responseCode = "404", description = "No devuelve nada"),
            @ApiResponse( responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<Cart> updateCart(@PathVariable("id") Long id, @RequestBody Cart cartDetails) {
        Cart updatedCart = cartService.updateCart(id, cartDetails);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{id}/product/{productId}")
    @Operation(summary = "Delete cart", description = "You need to pass the ID to delete the cart, this return nothing(void)")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable("id") Long id, @PathVariable("productId") Long productId) {
        cartService.removeProductFromCart(id, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/client/{clientId}")
    @Operation(summary = "Get cart by client id", description = "You need to pass the clientId, and it will return an object for that clientId")
    public ResponseEntity<List<Cart>> getCartsByClientId(@PathVariable("clientId") Long clientId) {
        List<Cart> carts = cartService.getCartsByClientId(clientId);
        return ResponseEntity.ok(carts);
    }
}
