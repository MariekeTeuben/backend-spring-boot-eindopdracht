package nl.novi.backendgarageservice.service;

import nl.novi.backendgarageservice.dto.RepairItemDto;
import nl.novi.backendgarageservice.exception.ResourceNotFoundException;
import nl.novi.backendgarageservice.model.Invoice;
import nl.novi.backendgarageservice.model.RepairItem;
import nl.novi.backendgarageservice.repository.InvoiceRepository;
import nl.novi.backendgarageservice.repository.RepairItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RepairItemService {
    private final RepairItemRepository repairItemRepos;
    private final InvoiceRepository invoiceRepos;


    public RepairItemService(RepairItemRepository repairItemRepos, InvoiceRepository invoiceRepos) {
        this.repairItemRepos = repairItemRepos;
        this.invoiceRepos = invoiceRepos;
    }

    public RepairItemDto getRepairItemById(Long id) {
        RepairItem repairItem = repairItemRepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Repair item cannot be found"));

        RepairItemDto repairItemDto = new RepairItemDto();
        repairItemDto.id = repairItem.getId();
        repairItemDto.itemName = repairItem.getItemName();
        repairItemDto.itemType = repairItem.getItemType();
        repairItemDto.itemDescription = repairItem.getItemDescription();
        repairItemDto.itemQuantity = repairItem.getItemQuantity();
        repairItemDto.itemPrice = repairItem.getItemPrice();
        repairItemDto.invoiceId = repairItem.getInvoice().getId();
        repairItemDto.invoice = invoiceRepos.findById(repairItemDto.invoiceId).get();

        return repairItemDto;
    }

    public List<RepairItemDto> getAllRepairItems() {
        ArrayList<RepairItemDto> repairItemDtoList = new ArrayList<>();
        Iterable<RepairItem> allRepairItems = repairItemRepos.findAll();
        for (RepairItem repairItem: allRepairItems) {
            RepairItemDto repairItemDto = new RepairItemDto();

            repairItemDto.id = repairItem.getId();
            repairItemDto.itemName = repairItem.getItemName();
            repairItemDto.itemType = repairItem.getItemType();
            repairItemDto.itemDescription = repairItem.getItemDescription();
            repairItemDto.itemQuantity = repairItem.getItemQuantity();
            repairItemDto.itemPrice = repairItem.getItemPrice();
            repairItemDto.invoiceId = repairItem.getInvoice().getId();
            repairItemDto.invoice = invoiceRepos.findById(repairItemDto.invoiceId).get();

            repairItemDtoList.add(repairItemDto);
        }

        if (repairItemDtoList.size() == 0) {
            throw new ResourceNotFoundException("Repair Items cannot be found");
        }

        return repairItemDtoList;

    }

    public Long createRepairItem(RepairItemDto repairItemDto) {
        RepairItem repairItem = new RepairItem();

        repairItem.setItemName(repairItemDto.itemName);
        repairItem.setItemType(repairItemDto.itemType);
        repairItem.setItemDescription(repairItemDto.itemDescription);
        repairItem.setItemQuantity(repairItemDto.itemQuantity);
        repairItem.setItemPrice(repairItemDto.itemPrice);

        Invoice invoice = invoiceRepos.findById(repairItemDto.invoiceId).get();
        repairItem.setInvoice(invoice);

        repairItemRepos.save(repairItem);

        repairItemDto.id = repairItem.getId();

        return repairItem.getId();
    }

    public Object updateRepairItem(Long id, RepairItemDto repairItemDto) {
        Optional<RepairItem> repairItem = repairItemRepos.findById(id);
        if (repairItem.isEmpty()) {
            throw new ResourceNotFoundException("No repair item with id:" + id);
        } else {
            RepairItem updatedRepairItem = repairItem.get();
            updatedRepairItem.setItemName(repairItemDto.itemName);
            updatedRepairItem.setItemType(repairItemDto.itemType);
            updatedRepairItem.setItemDescription(repairItemDto.itemDescription);
            updatedRepairItem.setItemQuantity(repairItemDto.itemQuantity);
            updatedRepairItem.setItemPrice(repairItemDto.itemPrice);
            repairItemRepos.save(updatedRepairItem);

            return updatedRepairItem;
        }
    }

    public Object deleteRepairItem(Long id) {
        Optional<RepairItem> optionalRepairItem = repairItemRepos.findById(id);
        if (optionalRepairItem.isEmpty()) {
            throw new ResourceNotFoundException("Repair item cannot be found");
        } else {
            RepairItem repairItem = optionalRepairItem.get();
            repairItemRepos.delete(repairItem);
            return "Repair item deleted successfully";
        }
    }
}

