package com.example.myLibraryServer.services;

import com.example.myLibraryServer.model.BookDetails;
import com.example.myLibraryServer.model.Publisher;
import com.example.myLibraryServer.repo.BookDetailsRepo;
import com.example.myLibraryServer.repo.PublisherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PublisherService {
    @Autowired
    PublisherRepo publisherRepo;
    @Autowired
    BookDetailsRepo bookDetailsRepo;

    public List<Publisher> getAllPublishers(){
        return publisherRepo.findByDeletedFalse();
    }

    public ResponseEntity<Map<String,String >> addPublisher(Publisher publisher){
        try {
            publisherRepo.save(publisher);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Publisher added successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Failed to add Publisher"));
        }
    }

    public List<BookDetails> generateReport(long publisherId){
        return bookDetailsRepo.findByPublisherId_IdAndNumOfCopiesGreaterThan(publisherId,0);
    }
    public ResponseEntity<Map<String,String >> deletePublisher(Long publisherId){
        try {
            Optional<Publisher> existingPublisherOptional = publisherRepo.findById(publisherId);
            if (existingPublisherOptional.isPresent()) {
                Publisher existingPublisher = existingPublisherOptional.get();
                List<BookDetails> activeBooks=bookDetailsRepo.findByPublisherId_IdAndNumOfCopiesGreaterThan(publisherId,0);
                System.out.println(activeBooks);
                if(!activeBooks.isEmpty())
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "You can't delete publisher since it has active books"));
                existingPublisher.setDeleted(true);
                publisherRepo.save(existingPublisher);
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Publisher deleted successfully"));

            } else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Publisher with ID " + publisherId + " not found"));


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to delete publisher"));
        }
    }
}
