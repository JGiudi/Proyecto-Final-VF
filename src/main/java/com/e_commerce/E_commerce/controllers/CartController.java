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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Cart> addProductToCart(
            @PathVariable("clientId") Long clientId,
            @PathVariable("productId") Long productId,
            @PathVariable("quantity") Integer quantity) {
        try {
            Cart cart = cartService.addProductToCart(clientId, productId, quantity);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/getAll")
    @Operation(summary = "Get all carts", description = "This returns all carts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carts retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Cart>> getAllCarts() {
        try {
            List<Cart> carts = cartService.getAllCarts();
            return ResponseEntity.ok(carts);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get one cart by ID", description = "You need to pass the ID, and it will return the cart with that ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Cart> getCartById(@PathVariable("id") Long id) {
        try {
            Optional<Cart> cart = cartService.getCartById(id);
            return cart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update cart", description = "You need to pass an object with the data for the cart, product, and quantity, and it returns the cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Cart not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Cart> updateCart(@PathVariable("id") Long id, @RequestBody Cart cartDetails) {
        try {
            Optional<Cart> cartOptional = cartService.getCartById(id);
            if (cartOptional.isPresent()) {
                Cart updatedCart = cartService.updateCart(id, cartDetails);
                return ResponseEntity.ok(updatedCart);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}/product/{productId}")
    @Operation(summary = "Delete product from cart", description = "You need to pass the ID to delete the product from the cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product removed from cart successfully"),
            @ApiResponse(responseCode = "404", description = "Cart or product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> removeProductFromCart(@PathVariable("id") Long id, @PathVariable("productId") Long productId) {
        try {
            Optional<Cart> cartOptional = cartService.getCartById(id);
            if (cartOptional.isPresent()) {
                cartService.removeProductFromCart(id, productId);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/client/{clientId}")
    @Operation(summary = "Get cart by client ID", description = "You need to pass the clientId, and it will return an object for that clientId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carts retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Cart>> getCartsByClientId(@PathVariable("clientId") Long clientId) {
        try {
            List<Cart> carts = cartService.getCartsByClientId(clientId);
            if (!carts.isEmpty()) {
                return ResponseEntity.ok(carts);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
