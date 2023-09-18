package nl.novi.backendgarageservice.controller;

import nl.novi.backendgarageservice.dto.InvoiceDto;
import nl.novi.backendgarageservice.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {this.invoiceService = invoiceService;}

    @GetMapping
    public ResponseEntity<ArrayList<InvoiceDto>> getAllInvoices() {
        return new ResponseEntity(invoiceService.getAllInvoices(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable Long id) {
        return new ResponseEntity(invoiceService.getInvoiceById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createInvoice(@Validated @RequestBody InvoiceDto invoiceDto, BindingResult br) {
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        }
        else {
            Long newId = invoiceService.createInvoice(invoiceDto);

            URI uri = URI.create(ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/" + newId).toUriString());

            invoiceDto.id = newId;

            return ResponseEntity.created(uri).body(invoiceService.getInvoiceById(newId));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateInvoice(@PathVariable Long id, @RequestBody InvoiceDto invoiceDto) {
        return ResponseEntity.ok(invoiceService.updateInvoice(id, invoiceDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteInvoice(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.deleteInvoice(id));
    }

}
