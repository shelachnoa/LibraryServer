package com.example.myLibraryServer.services;

import com.example.myLibraryServer.model.Borrowing;
import com.example.myLibraryServer.model.Reader;
import com.example.myLibraryServer.repo.ReaderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReaderService {
    @Autowired
    ReaderRepo readerRepo;
    @Autowired
    BorrowingService borrowingService;

    public List<Reader> getAllReaders(){
        return readerRepo.findByDeletedFalse();
    }
    public ResponseEntity<Map<String,String>> addReader(Reader reader){
        try {
            readerRepo.save(reader);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Reader added successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to add Reader"));
        }
    }

    public ResponseEntity<Map<String,String>> deleteReader(long readerId){
        try {
            Optional<Reader> existingReaderOptional = readerRepo.findById(readerId);
            if (existingReaderOptional.isPresent()) {
                Reader existingReader = existingReaderOptional.get();
                List<Borrowing> activeBorrowings=borrowingService.getBorrowingsByReaderId(readerId);
                if (!activeBorrowings.isEmpty())
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "You can't delete reader since it has open borrowing"));

                existingReader.setDeleted(true);
                readerRepo.save(existingReader);
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Reader deleted successfully"));

            } else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Reader with ID " + readerId + " not found\""));


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to delete Reader"));
        }
    }
}
