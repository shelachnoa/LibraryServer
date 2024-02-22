package com.example.myLibraryServer.controllers;

import com.example.myLibraryServer.model.Book;
import com.example.myLibraryServer.model.BookDetails;
import com.example.myLibraryServer.model.Reader;
import com.example.myLibraryServer.repo.BookRepo;
import com.example.myLibraryServer.services.BookService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class BookController {

    @Autowired
    EntityManager entityManager;
    @Autowired
    BookService bookService;

    //will be used for copies
    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/books/copies/{bookCode}")
    public List<Book> getCopies(@PathVariable long bookCode) {
        return bookService.getCopiesOfBook(bookCode);
    }

    @GetMapping("/books/unBorrowed")
    public List<Book> getUnBorrowedBooks() {

        return bookService.getAllUnborrowedBooks();
    }

    //used to add copy
    @PostMapping("/books/addBook/{amount}")
    public ResponseEntity<Map<String, String>> addBook(@PathVariable int amount, @RequestBody BookDetails book) {
        return bookService.addCopies(amount,book);
    }


    @PutMapping("/books/delete/{bookId}")
    public ResponseEntity<Map<String, String>> deleteBook(@PathVariable Long bookId) {
        return bookService.deleteBook(bookId);
    }

}


