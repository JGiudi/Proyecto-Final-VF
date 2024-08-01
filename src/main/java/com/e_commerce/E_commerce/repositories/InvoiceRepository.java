package com.e_commerce.E_commerce.repositories;

import com.e_commerce.E_commerce.entities.Client;
import com.e_commerce.E_commerce.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByClientIdOrderByCreatedAtDesc(Long clientId);
}
