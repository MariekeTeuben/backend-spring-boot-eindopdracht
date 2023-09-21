package nl.novi.backendgarageservice.service;

import nl.novi.backendgarageservice.dto.InvoiceDto;
import nl.novi.backendgarageservice.exception.ResourceNotFoundException;
import nl.novi.backendgarageservice.model.Invoice;
import nl.novi.backendgarageservice.model.RepairJob;
import nl.novi.backendgarageservice.repository.InvoiceRepository;
import nl.novi.backendgarageservice.repository.RepairJobRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepos;


    public InvoiceService(InvoiceRepository invoiceRepos) {
        this.invoiceRepos = invoiceRepos;
    }

    public InvoiceDto getInvoiceById(Long id) {
        Invoice invoice = invoiceRepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice cannot be found"));

        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.id = invoice.getId();
        invoiceDto.date = invoice.getDate();
        invoiceDto.tax = invoice.getTax();
        invoiceDto.total = invoice.getTotal();

        if(invoice.getRepairJobs() != null) {
            for (RepairJob repairJob : invoice.getRepairJobs()) {
                invoiceDto.repairJobs.add(repairJob.getJobName());
            }
        }

        return invoiceDto;
    }

    public List<InvoiceDto> getAllInvoices() {
        ArrayList<InvoiceDto> invoiceDtoList = new ArrayList<>();
        Iterable<Invoice> allInvoices = invoiceRepos.findAll();
        for (Invoice invoice: allInvoices) {
            InvoiceDto invoiceDto = new InvoiceDto();

            invoiceDto.id = invoice.getId();
            invoiceDto.date = invoice.getDate();
            invoiceDto.tax = invoice.getTax();
            invoiceDto.total = invoice.getTotal();

            if(invoice.getRepairJobs() != null) {
                for (RepairJob repairJob : invoice.getRepairJobs()) {
                    invoiceDto.repairJobs.add(repairJob.getJobName());
                }
            }

            invoiceDtoList.add(invoiceDto);
        }

        if (invoiceDtoList.size() == 0) {
            throw new ResourceNotFoundException("Invoices cannot be found");
        }

        return invoiceDtoList;
    }

    public Long createInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = new Invoice();

        invoice.setDate(invoiceDto.date);
        invoice.setTax(invoiceDto.tax);
        invoice.setTotal(invoiceDto.total);

        invoiceRepos.save(invoice);

        invoiceDto.id = invoice.getId();

        return invoice.getId();
    }

    public Object updateInvoice(Long id, InvoiceDto invoiceDto) {
        Optional<Invoice> invoice = invoiceRepos.findById(id);
        if (invoice.isEmpty()) {
            throw new ResourceNotFoundException("No invoice with id:" + id);
        } else {
            Invoice updatedInvoice = invoice.get();
            updatedInvoice.setDate(invoiceDto.date);
            updatedInvoice.setTax(invoiceDto.tax);
            updatedInvoice.setTotal(invoiceDto.total);

            invoiceRepos.save(updatedInvoice);

            return updatedInvoice;
        }
    }

    public Object deleteInvoice(Long id) {
        Optional<Invoice> optionalInvoice = invoiceRepos.findById(id);
        if (optionalInvoice.isEmpty()) {
            throw new ResourceNotFoundException("Invoice cannot be found");
        } else {
            Invoice invoice = optionalInvoice.get();
            invoiceRepos.delete(invoice);
            return "Invoice deleted successfully";
        }
    }
}
