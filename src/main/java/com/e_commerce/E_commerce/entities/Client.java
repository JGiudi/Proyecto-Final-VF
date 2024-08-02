package com.e_commerce.E_commerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the client", example = "1")
    private Long id;

    @Schema(description = "Name of the client", example = "Joaquin")
    @Getter @Setter
    private String name;

    @Schema(description = "Surname of the client", example = "Giudice")
    @Getter @Setter
    private String surname;

    @Schema(description = "Email of the client", example = "J.Giudice@gmail.com")
    @Getter @Setter
    private String email;

    @Schema(description = "Dni of the client", example = "44547072")
    @Getter @Setter
    private Integer dni;

    @Schema(description = "Points that the client has to use for purchases", example = "1000000")
    @Getter @Setter
    private Integer points;

    @OneToMany(mappedBy = "client")
    @Schema(description = "List of invoices associated with the client")
    @Getter @Setter
    @JsonIgnore
    private List<Invoice> invoices;

}
