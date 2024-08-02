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

@RestController
@RequestMapping("/api/v1/invoices")
@Tag(name = "Invoices path", description = "CRUD operations for invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    @Operation(summary = "Create invoice", description = "Generates an invoice for the client's cart and marks the cart as delivered")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice created successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found or no cart found")
    })
    public ResponseEntity<Invoice> createInvoice(@RequestParam Long clientId) {
        try {
            Invoice invoice = invoiceService.createInvoice(clientId);
            return ResponseEntity.ok(invoice);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{clientId}")
    @Operation(summary = "Get last invoice by client ID", description = "Fetches the latest invoice for the specified client ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Invoice not found for client")
    })
    public ResponseEntity<Invoice> getLastInvoiceForClient(@PathVariable Long clientId) {
        try {
            Invoice invoice = invoiceService.getLastInvoiceForClient(clientId);
            return ResponseEntity.ok(invoice);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
