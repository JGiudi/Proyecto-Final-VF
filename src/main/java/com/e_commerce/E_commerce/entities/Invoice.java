package com.e_commerce.E_commerce.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "Invoices")
@Schema(description = "Represents an invoice in the e-commerce system.")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the invoice", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @Schema(description = "Client associated with the invoice")
    @Getter @Setter
    private Client client;

    @ElementCollection
    @CollectionTable(name = "invoice_products", joinColumns = @JoinColumn(name = "invoice_id"))
    @Column(name = "product_id")
    @Schema(description = "List of product IDs included in the invoice")
    @ArraySchema(arraySchema = @Schema(description = "List of product IDs"))
    @Getter @Setter
    private List<Long> productIds;

    @Schema(description = "Total amount of the invoice", example = "150.75")
    @Getter @Setter
    private Double totalAmount;

    @Schema(description = "Indicates whether the invoice has been delivered", example = "false")
    @Getter @Setter
    private boolean delivered;

    @Column(name = "created_at")
    @Schema(description = "Creation date of the invoice")
    @Getter @Setter
    private LocalDateTime createdAt;
}
