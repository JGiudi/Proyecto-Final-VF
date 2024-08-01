package com.e_commerce.E_commerce.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "Products")
@Schema(description = "Represents a product in the e-commerce system.")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the product", example = "1")
    private Long id;

    @Schema(description = "Name of the product", example = "Iphone 15 pro max")
    @Getter @Setter
    private String name;

    @Schema(description = "Description of the product", example = "Latest model of Apple")
    @Getter @Setter
    private String description;

    @Schema(description = "Stock quantity of the product", example = "50")
    @Getter @Setter
    private Integer stock;

    @Schema(description = "Price of the product", example = "699.99")
    @Getter @Setter
    private Double price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @Schema(description = "Reference to a parent product, if applicable")
    private Product product;


    public Long getId() {
        return id;
    }
}
