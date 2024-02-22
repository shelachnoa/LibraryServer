package com.example.myLibraryServer.services;

import com.example.myLibraryServer.model.Book;
import com.example.myLibraryServer.model.BookDetails;
import com.example.myLibraryServer.model.Borrowing;
import com.example.myLibraryServer.repo.BookDetailsRepo;
import com.example.myLibraryServer.repo.BookRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepo bookRepo;
    @Autowired
    BookDetailsRepo bookDetailsRepo;
    @Autowired
    EntityManager entityManager;

    public List<Book> getAllBooks() {
        return bookRepo.findAllByDeletedFalse();
    }

    public List<Book> getAllUnborrowedBooks() {
        return bookRepo.findAllByIsBorrowedFalseAndDeletedFalse();
    }

    public Book getBookById(long bookId) {
        Optional<Book> existingBook = bookRepo.findById(bookId);
        return existingBook.get();
    }

    public List<Book> getCopiesOfBook(long bookCode) {
        return bookRepo.findByBookCode_CodeAndDeletedIsFalse(bookCode);
    }
    public ResponseEntity<Map<String, String>> addBooks(int amount,BookDetails book) {
        try {
            System.out.println(amount);
//            if (amount==0)
//                return  ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "No Copy added"));
            for (int i=0;i<amount;i++) {
                Book bookInstance = new Book();
                bookInstance.setBookCode(book);
                bookRepo.save(bookInstance);
                System.out.println("book saved");
            }
//            book.setNumOfCopies(book.getNumOfCopies()+amount);
//            bookDetailsRepo.save(book);
//            System.out.println(book.getNumOfCopies());
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", amount+" Book added successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to add Book"));
        }
    }

    //will be used for adding copies
    public ResponseEntity<Map<String, String>> addCopies(int amount,BookDetails book) {
        try {
            System.out.println(amount);
//            if (amount==0)
//                return  ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "No Copy added"));
            for (int i=0;i<amount;i++) {
                Book bookInstance = new Book();
                bookInstance.setBookCode(book);
                bookRepo.save(bookInstance);
                System.out.println("copy saved");
            }
            book.setNumOfCopies(book.getNumOfCopies()+amount);
            bookDetailsRepo.save(book);
            System.out.println(book.getNumOfCopies());
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", amount+" Copies added successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to add Copy"));
        }
    }

    public ResponseEntity<Map<String, String>> deleteBook(long bookId) {
        try {
            Optional<Book> existingBookOptional = bookRepo.findById(bookId);
            if (existingBookOptional.isPresent()) {
                Book existingBook = existingBookOptional.get();
                if (existingBook.getIsBorrowed())
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Can't delete Copy since it's borrowed"));
                existingBook.setDeleted(true);
                Optional<BookDetails> existingBookDetailsOptional = bookDetailsRepo.findById(existingBook.getBookCode().getCode());
                if (existingBookDetailsOptional.isPresent()) {
                    BookDetails existingBookDetails = existingBookDetailsOptional.get();
                    existingBookDetails.setNumOfCopies(existingBookDetails.getNumOfCopies() - 1);
                }
                bookRepo.save(existingBook);
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Copy deleted successfully"));
            } else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Copy with ID " + bookId + " not found\""));


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.ordinal()).body(Map.of("message", "Failed to delete Copy"));
        }
    }


}
