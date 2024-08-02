package com.e_commerce.E_commerce.repositories;

import com.e_commerce.E_commerce.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findFirstByClient_IdOrderByCreatedAtDesc(Long clientId);
}
