package com.e_commerce.E_commerce.controllers;

import com.e_commerce.E_commerce.entities.Invoice;
import com.e_commerce.E_commerce.services.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invoices")
@Tag(name = "Invoices path", description = "CRUD operations for e-commerce")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/create/{clientId}")
    @Operation(summary = "Create an invoice", description = "Creates an invoice for a given client ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Invoice> createInvoice(@PathVariable Long clientId) {
        try {
            Invoice invoice = invoiceService.createInvoice(clientId);
            return ResponseEntity.ok(invoice);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/last/{clientId}")
    @Operation(summary = "Get last invoice", description = "Retrieves the last invoice for a given client ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Client or invoice not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Invoice> getLastInvoice(@PathVariable Long clientId) {
        try {
            Invoice invoice = invoiceService.getLastInvoiceForClient(clientId);
            if (invoice != null) {
                return ResponseEntity.ok(invoice);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Get all invoices", description = "Retrieves all invoices")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoices retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        try {
            List<Invoice> invoices = invoiceService.getAllInvoices();
            return ResponseEntity.ok(invoices);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete an invoice", description = "Deletes an invoice by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Invoice deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Invoice not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        try {
            boolean isRemoved = invoiceService.deleteInvoice(id);
            if (isRemoved) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
