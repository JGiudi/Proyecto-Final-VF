package com.e_commerce.E_commerce.controllers;

import com.e_commerce.E_commerce.entities.Client;
import com.e_commerce.E_commerce.services.ClientsService;
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
@RequestMapping("/api/v1/auth")
@Tag(name = "Clients path", description = "CRUD operations for e-commerce")
public class ClientController {

    @Autowired
    private ClientsService clientService;

    @PostMapping("/register")
    @Operation(summary = "Register a new client", description = "Creates a new client and returns the created client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client created successfully"),
            @ApiResponse(responseCode = "404", description = "Client creation failed")
    })
    public ResponseEntity<Client> registerClient(@RequestBody Client client) {
        try {
            Client createdClient = clientService.createClient(client);
            return ResponseEntity.ok(createdClient);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/me/{id}")
    @Operation(summary = "Get client by ID", description = "Retrieves a client by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<Client> getClientById(@PathVariable("id") Long id) {
        try {
            Optional<Client> client = clientService.getClientById(id);
            return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/clients")
    @Operation(summary = "Get all clients", description = "Retrieves a list of all clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clients retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No clients found")
    })
    public ResponseEntity<List<Client>> getAllClients() {
        try {
            List<Client> clients = clientService.getAllClients();
            return clients.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(clients);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/me/{id}")
    @Operation(summary = "Update client details", description = "Updates the details of an existing client by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client updated successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<Client> updateClient(@PathVariable("id") Long id, @RequestBody Client clientDetails) {
        try {
            Optional<Client> existingClient = clientService.getClientById(id);
            if (existingClient.isPresent()) {
                Client updatedClient = clientService.updateClient(id, clientDetails);
                return ResponseEntity.ok(updatedClient);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete client by ID", description = "Deletes a client by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Client deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<Void> deleteClient(@PathVariable("id") Long id) {
        try {
            Optional<Client> client = clientService.getClientById(id);
            if (client.isPresent()) {
                clientService.deleteClient(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
