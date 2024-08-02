package com.e_commerce.E_commerce.services;

import com.e_commerce.E_commerce.entities.Cart;
import com.e_commerce.E_commerce.entities.Client;
import com.e_commerce.E_commerce.entities.Invoice;
import com.e_commerce.E_commerce.entities.Product;
import com.e_commerce.E_commerce.repositories.CartRepository;
import com.e_commerce.E_commerce.repositories.ClientRepository;
import com.e_commerce.E_commerce.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Invoice createInvoice(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Cart cart = cartRepository.findByClientIdAndDeliveredFalse(clientId);
        if (cart == null || cart.getProducts().isEmpty()) {
            throw new RuntimeException("No cart found or cart is empty");
        }

        double totalAmount = cart.getQuantities().stream()
                .mapToDouble(q -> cart.getProducts().get(cart.getQuantities().indexOf(q)).getPrice() * q)
                .sum();

        Invoice invoice = new Invoice();
        invoice.setClient(client);
        invoice.setProductIds(cart.getProducts().stream().map(Product::getId).toList());
        invoice.setTotalAmount(totalAmount);
        invoice.setDelivered(true);
        invoice.setCreatedAt(LocalDateTime.now());

        cart.setDelivered(true); // Mark cart as delivered
        cartRepository.save(cart);

        return invoiceRepository.save(invoice);
    }

    public Invoice getLastInvoiceForClient(Long clientId) {
        return invoiceRepository.findFirstByClient_IdOrderByCreatedAtDesc(clientId)
                .orElseThrow(() -> new RuntimeException("No invoices found for client"));
    }
}
