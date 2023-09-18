package nl.novi.backendgarageservice.service;

import nl.novi.backendgarageservice.dto.RepairJobDto;
import nl.novi.backendgarageservice.exception.ResourceNotFoundException;
import nl.novi.backendgarageservice.model.Invoice;
import nl.novi.backendgarageservice.model.RepairItem;
import nl.novi.backendgarageservice.model.RepairJob;
import nl.novi.backendgarageservice.repository.InvoiceRepository;
import nl.novi.backendgarageservice.repository.RepairJobRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RepairJobService {
    private final RepairJobRepository repairJobRepos;
    private final InvoiceRepository invoiceRepos;

    public RepairJobService(RepairJobRepository repairJobRepos, InvoiceRepository invoiceRepos) {
        this.repairJobRepos = repairJobRepos;
        this.invoiceRepos = invoiceRepos;
    }


    public RepairJobDto getRepairJobById(Long id) {
        RepairJob repairJob = repairJobRepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Repair job cannot be found"));

        RepairJobDto repairJobDto = new RepairJobDto();
        repairJobDto.id = repairJob.getId();
        repairJobDto.jobName = repairJob.getJobName();

        if(repairJob.getRepairItems() != null) {
            for (RepairItem repairItem : repairJob.getRepairItems()) {
                repairJobDto.repairItems.add(repairItem.getItemName());
            }
        }

        return repairJobDto;
    }

    public List<RepairJobDto> getAllRepairJobs() {
        ArrayList<RepairJobDto> repairJobDtoList = new ArrayList<>();
        Iterable<RepairJob> allRepairJobs = repairJobRepos.findAll();
        for (RepairJob repairJob: allRepairJobs) {
            RepairJobDto repairJobDto = new RepairJobDto();

            repairJobDto.id = repairJob.getId();
            repairJobDto.jobName = repairJob.getJobName();

            if(repairJob.getRepairItems() != null) {
                for (RepairItem repairItem : repairJob.getRepairItems()) {
                    repairJobDto.repairItems.add(repairItem.getItemName());
                }
            }

            repairJobDtoList.add(repairJobDto);
        }

        if (repairJobDtoList.size() == 0) {
            throw new ResourceNotFoundException("Repair Jobs cannot be found");
        }

        return repairJobDtoList;

    }

    public Long createRepairJob(RepairJobDto repairJobDto) {
        RepairJob repairJob = new RepairJob();

        repairJob.setJobName(repairJobDto.jobName);

        Invoice invoice = invoiceRepos.findById(repairJobDto.invoiceId).get();
        repairJob.setInvoice(invoice);

        repairJobRepos.save(repairJob);

        repairJobDto.id = repairJob.getId();

        return repairJob.getId();
    }

    public Object updateRepairJob(Long id, RepairJobDto repairJobDto) {
        Optional<RepairJob> repairJob = repairJobRepos.findById(id);
        if (repairJob.isEmpty()) {
            throw new ResourceNotFoundException("No repair job with id:" + id);
        } else {
            RepairJob updatedRepairJob = repairJob.get();
            updatedRepairJob.setJobName(repairJobDto.jobName);
            repairJobRepos.save(updatedRepairJob);

            return updatedRepairJob;
        }
    }

    public Object deleteRepairJob(Long id) {
        Optional<RepairJob> optionalRepairJob = repairJobRepos.findById(id);
        if (optionalRepairJob.isEmpty()) {
            throw new ResourceNotFoundException("Repair job cannot be found");
        } else {
            RepairJob repairJob = optionalRepairJob.get();
            repairJobRepos.delete(repairJob);
            return "Repair job deleted successfully";
        }
    }
}
