package com.e_commerce.E_commerce.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "Clients")
@Schema(description = "Represents a client")
public class Client {
    @Schema(description = "Unique identifier of the clients", example = "1")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Name of the client", example = "Lionel")
    @Getter @Setter private String name;
    @Schema(description = "Surname of the client", example = "Messi")
    @Getter @Setter private String surname;
    @Schema(description = "Email of the client", example = "leomessi@gmail.com")
    @Getter @Setter private String email;
    @Schema(description = "Dni of the client", example = "22456772")
    @Getter @Setter private Integer dni;
    @Schema(description = "Points that the client has to use for purchases", example = "1000")
    @Getter @Setter private Integer points;

    @OneToMany(mappedBy = "client")
    @Schema(description = "List of invoices associated with the client")
    @Getter @Setter
    private List<Invoice> invoices;
}
