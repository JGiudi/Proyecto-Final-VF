package com.e_commerce.E_commerce.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "Carts")
@Schema(description = "Represents a shopping cart in the e-commerce system.")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the cart", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @Schema(description = "Client associated with the cart")
    @Getter @Setter
    private Client client;

    @ManyToMany
    @JoinTable(
            name = "cart_products",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @Schema(description = "List of products in the cart")
    @ArraySchema(arraySchema = @Schema(description = "List of products"))
    @Getter @Setter
    private List<Product> products = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "cart_quantities", joinColumns = @JoinColumn(name = "cart_id"))
    @Column(name = "quantity")
    @Schema(description = "Quantities of each product in the cart")
    @ArraySchema(arraySchema = @Schema(description = "List of quantities"))
    @Getter @Setter
    private List<Integer> quantities = new ArrayList<>();

    @Schema(description = "Indicates whether the cart has been delivered", example = "false")
    @Getter @Setter
    private boolean delivered;
}
