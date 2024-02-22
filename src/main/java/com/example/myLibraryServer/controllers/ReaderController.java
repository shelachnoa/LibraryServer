package com.example.myLibraryServer.controllers;


import com.example.myLibraryServer.model.Book;
import com.example.myLibraryServer.model.Publisher;
import com.example.myLibraryServer.model.Reader;
import com.example.myLibraryServer.repo.ReaderRepo;
import com.example.myLibraryServer.services.PublisherService;
import com.example.myLibraryServer.services.ReaderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class ReaderController {
    @Autowired
    ReaderRepo readerRepo;
    @Autowired
    EntityManager entityManager;

    @Autowired
    ReaderService readerService;

    @GetMapping("/readers")
    public List<Reader> getReaders() {
        return readerService.getAllReaders();
    }

    @PostMapping("/readers/addReader")
    public ResponseEntity<Map<String, String>> addPublisher(@RequestBody Reader reader) {
        return readerService.addReader(reader);
    }

    @PutMapping("/readers/delete/{readerId}")
    public ResponseEntity<Map<String, String>> deletePublisher(@PathVariable long readerId) {
        return readerService.deleteReader(readerId);
    }

}
