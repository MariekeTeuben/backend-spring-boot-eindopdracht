package nl.novi.backendgarageservice.controller;

import nl.novi.backendgarageservice.dto.FileDto;
import nl.novi.backendgarageservice.exception.ResourceNotFoundException;
import nl.novi.backendgarageservice.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFileById(@PathVariable Long id) {
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + fileService.getFileById(id).fileName + "\"")
                .body(fileService.getFileById(id).docFile);
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam() MultipartFile file) throws java.io.IOException {
        if (file.isEmpty()) {
            throw new ResourceNotFoundException("Please upload a file");
    }

    FileDto uploadedFile = fileService.uploadFile(file);
    URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + uploadedFile.id)));
    return ResponseEntity.created(uri).body("File uploaded successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFile(@PathVariable Long id) {
        return ResponseEntity.ok(fileService.deleteFile(id));
    }
}
