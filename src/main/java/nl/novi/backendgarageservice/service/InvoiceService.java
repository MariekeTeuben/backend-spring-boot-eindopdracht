package nl.novi.backendgarageservice.service;

import nl.novi.backendgarageservice.dto.InvoiceDto;
import nl.novi.backendgarageservice.exception.ResourceNotFoundException;
import nl.novi.backendgarageservice.model.Invoice;
import nl.novi.backendgarageservice.model.RepairItem;
import nl.novi.backendgarageservice.model.User;
import nl.novi.backendgarageservice.repository.InvoiceRepository;
import nl.novi.backendgarageservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepos;
    private final UserRepository userRepos;


    public InvoiceService(InvoiceRepository invoiceRepos, UserRepository userRepos) {
        this.invoiceRepos = invoiceRepos;
        this.userRepos = userRepos;
    }

    public InvoiceDto getInvoiceById(Long id) {
        Invoice invoice = invoiceRepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice cannot be found"));

        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.id = invoice.getId();
        invoiceDto.date = invoice.getDate();
        invoiceDto.username = invoice.getUser().getUsername();

        invoiceDto.totalPrice = 0.0;

        if(invoice.getRepairItems() != null) {
            for (RepairItem repairItem : invoice.getRepairItems()) {
                invoiceDto.totalPrice = invoiceDto.totalPrice + (repairItem.getItemPrice()) * (repairItem.getItemQuantity());
                invoiceDto.repairItems.add(repairItem.getItemName());
            }
        }

        invoiceDto.tax = invoiceDto.totalPrice * 0.21;

        return invoiceDto;
    }

    public List<InvoiceDto> getAllInvoices() {
        ArrayList<InvoiceDto> invoiceDtoList = new ArrayList<>();
        Iterable<Invoice> allInvoices = invoiceRepos.findAll();
        for (Invoice invoice: allInvoices) {
            InvoiceDto invoiceDto = new InvoiceDto();

            invoiceDto.id = invoice.getId();
            invoiceDto.date = invoice.getDate();
            invoiceDto.username = invoice.getUser().getUsername();

            invoiceDto.totalPrice = 0.0;

            if(invoice.getRepairItems() != null) {
                for (RepairItem repairItem : invoice.getRepairItems()) {
                    invoiceDto.totalPrice = invoiceDto.totalPrice + (repairItem.getItemPrice()) * (repairItem.getItemQuantity());
                    invoiceDto.repairItems.add(repairItem.getItemName());
                }
            }

            invoiceDto.tax = invoiceDto.totalPrice * 0.21;

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

        User user = userRepos.findById(invoiceDto.username).get();
        invoice.setUser(user);

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
