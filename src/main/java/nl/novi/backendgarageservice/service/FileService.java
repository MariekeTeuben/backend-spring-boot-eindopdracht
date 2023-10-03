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

    public ArrayList<FileDto> getAllFiles() {
        ArrayList<FileDto> fileDtoList = new ArrayList<>();
        Iterable<File> allFiles = fileRepos.findAll();
        for (File file : allFiles) {
            FileDto fileDto = new FileDto();

            fileDto.id = file.getId();
            fileDto.fileName = file.getFileName();
            fileDto.docFile = file.getDocFile();


            fileDtoList.add(fileDto);
        }

        if (fileDtoList.size() == 0) {
            throw new ResourceNotFoundException("Files cannot be found");
        }

        return fileDtoList;
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


    public Object updateFile(Long id, FileDto fileDto) {
        Optional<File> optionalFile = fileRepos.findById(id);
        if (optionalFile.isEmpty()) {
            throw new ResourceNotFoundException("File cannot be found");
        } else {
            File updatedFile = optionalFile.get();
            updatedFile.setFileName(fileDto.fileName);
            updatedFile.setDocFile(fileDto.docFile);

            fileRepos.save(updatedFile);

            return updatedFile;
        }
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

