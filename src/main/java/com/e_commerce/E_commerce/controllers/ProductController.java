package com.e_commerce.E_commerce.controllers;

import com.e_commerce.E_commerce.entities.Product;
import com.e_commerce.E_commerce.services.ProductService;
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
@RequestMapping("/api/v1/products")
@Tag(name = "Products path", description = "CRUD operations for e-commerce")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product", description = "Creates a new product and returns the created product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created successfully"),
            @ApiResponse(responseCode = "404", description = "Product creation failed")
    })
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product createdProduct = productService.createProduct(product);
            return ResponseEntity.ok(createdProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieves a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        try {
            Optional<Product> product = productService.getProductById(id);
            return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieves a list of all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No products found")
    })
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return products.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product details", description = "Updates the details of an existing product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product productDetails) {
        try {
            if (productService.getProductById(id).isPresent()) {
                Product updatedProduct = productService.updateProduct(id, productDetails);
                return ResponseEntity.ok(updatedProduct);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by ID", description = "Deletes a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        try {
            if (productService.getProductById(id).isPresent()) {
                productService.deleteProduct(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
