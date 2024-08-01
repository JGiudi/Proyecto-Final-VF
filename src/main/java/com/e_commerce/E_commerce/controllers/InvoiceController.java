package com.e_commerce.E_commerce.controllers;


import com.e_commerce.E_commerce.entities.Invoice;
import com.e_commerce.E_commerce.services.InvoiceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/invoices")
@Tag(name = "Invoices path", description = "CRUD operations for e-commerce")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/create/{clientId}")
    public ResponseEntity<Invoice> createInvoice(@PathVariable Long clientId) {
        Invoice invoice = invoiceService.createInvoice(clientId);
        return ResponseEntity.ok(invoice);
    }

    @GetMapping("/last/{clientId}")
    public ResponseEntity<Invoice> getLastInvoice(@PathVariable Long clientId) {
        Invoice invoice = invoiceService.getLastInvoiceForClient(clientId);
        return ResponseEntity.ok(invoice);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}