package com.e_commerce.E_commerce.services;

import com.e_commerce.E_commerce.entities.Cart;
import com.e_commerce.E_commerce.entities.Invoice;
import com.e_commerce.E_commerce.entities.Product;
import com.e_commerce.E_commerce.repositories.CartRepository;
import com.e_commerce.E_commerce.repositories.InvoiceRepository;
import com.e_commerce.E_commerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Invoice createInvoice(Long clientId) {
        Cart cart = cartRepository.findByClientIdAndDeliveredFalse(clientId);
        if (cart == null) {
            throw new RuntimeException("No undelivered cart found for client with ID " + clientId);
        }

        double totalAmount = 0.0;
        for (int i = 0; i < cart.getProducts().size(); i++) {
            totalAmount += cart.getProducts().get(i).getPrice() * cart.getQuantities().get(i);
        }

        Invoice invoice = new Invoice();
        invoice.setClient(cart.getClient());
        invoice.setProductIds(cart.getProducts().stream().map(Product::getId).toList());
        invoice.setTotalAmount(totalAmount);
        invoice.setDelivered(false);
        invoice.setCreatedAt(new Date());

        Invoice savedInvoice = invoiceRepository.save(invoice);

        cart.setDelivered(true);
        cartRepository.save(cart);

        updateProductStock(cart);

        return savedInvoice;
    }

    private void updateProductStock(Cart cart) {
        for (int i = 0; i < cart.getProducts().size(); i++) {
            Product product = cart.getProducts().get(i);
            int quantity = cart.getQuantities().get(i);
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
        }
    }

    public Invoice getLastInvoiceForClient(Long clientId) {
        List<Invoice> invoices = invoiceRepository.findByClientIdOrderByCreatedAtDesc(clientId);
        if (invoices.isEmpty()) {
            throw new RuntimeException("No invoices found for client with ID " + clientId);
        }
        return invoices.get(0);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public boolean deleteInvoice(Long id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isPresent()) {
            invoiceRepository.delete(invoice.get());
            return true;
        } else {
            return false;
        }
    }
}
