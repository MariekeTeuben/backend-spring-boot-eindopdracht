package nl.novi.backendgarageservice.service;

import nl.novi.backendgarageservice.dto.FileDto;
import nl.novi.backendgarageservice.exception.ResourceNotFoundException;
import nl.novi.backendgarageservice.model.File;
import nl.novi.backendgarageservice.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class FileService {

    private final FileRepository fileRepos;

    public FileService(FileRepository fileRepos) {
        this.fileRepos = fileRepos;
    }

    public FileDto getFileById(Long id) {
        File file = fileRepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("File cannot be found"));

        FileDto fileDto = new FileDto();
        fileDto.id = file.getId();
        fileDto.fileName = file.getFileName();
        fileDto.docFile = file.getDocFile();

        return fileDto;
    }


    public FileDto uploadFile(MultipartFile fileUpload) throws java.io.IOException {

        File newFile = new File();
        newFile.setFileName(fileUpload.getOriginalFilename());
        newFile.setDocFile(fileUpload.getBytes());

        fileRepos.save(newFile);

        FileDto fileDto = new FileDto();
        fileDto.fileName = newFile.getFileName();
        fileDto.docFile = newFile.getDocFile();

        return fileDto;
    }

    public Object deleteFile(Long id) {
        Optional<File> optionalFile = fileRepos.findById(id);
        if (optionalFile.isEmpty()) {
            throw new ResourceNotFoundException("File cannot be found");
        } else {
            File file = optionalFile.get();
            fileRepos.delete(file);
            return "File deleted successfully";
        }
    }
}

