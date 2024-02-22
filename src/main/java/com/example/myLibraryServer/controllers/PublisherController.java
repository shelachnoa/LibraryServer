package com.example.myLibraryServer.controllers;

import com.example.myLibraryServer.model.Book;
import com.example.myLibraryServer.model.BookDetails;
import com.example.myLibraryServer.model.Borrowing;
import com.example.myLibraryServer.model.Publisher;
import com.example.myLibraryServer.repo.BookDetailsRepo;
import com.example.myLibraryServer.repo.PublisherRepo;
import com.example.myLibraryServer.services.PublisherService;
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
public class PublisherController {
    @Autowired
    PublisherRepo publisherRepo;
    @Autowired
    BookDetailsRepo bookDetailsRepo;
    @Autowired
    EntityManager entityManager;
    @Autowired
    PublisherService publisherService;

    @GetMapping("/publishers")
    public List<Publisher> getPublishers() {
        return publisherService.getAllPublishers();
    }

    @PostMapping("/publishers/addPublisher")
    public ResponseEntity<Map<String, String>> addPublisher(@RequestBody Publisher publisher) {
        return publisherService.addPublisher(publisher);
    }

    @GetMapping("/publishers/report/{publisherId}")
    public List<BookDetails> generateReport(@PathVariable Long publisherId) {
        return publisherService.generateReport(publisherId);

    }

    @PutMapping("/publishers/delete/{publisherId}")
    public ResponseEntity<Map<String, String>> deletePublisher(@PathVariable Long publisherId) {
        return publisherService.deletePublisher(publisherId);
    }
}
