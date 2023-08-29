package nl.novi.backendgarageservice.controller;

import nl.novi.backendgarageservice.model.Invoice;
import nl.novi.backendgarageservice.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceRepository repos;

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        return ResponseEntity.ok(repos.findAll());
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        repos.save(invoice);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/" + invoice.getId()).toUriString());
        return ResponseEntity.created(uri).body(invoice);
    }
}
