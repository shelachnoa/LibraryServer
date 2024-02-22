package com.example.myLibraryServer.controllers;


import com.example.myLibraryServer.model.Book;
import com.example.myLibraryServer.model.BookDetails;

import com.example.myLibraryServer.services.BookDetailsService;
import com.example.myLibraryServer.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class BookDetailsController {
    @Autowired
    BookDetailsService bookDetailsService;

    //adding new book to system
    @GetMapping("/bookDetails")
    public List<BookDetails> getBooksDetails() {
        return bookDetailsService.getBooksDetails();
    }

    //will be used for new book
    @PostMapping("/bookDetails/addBookDetails")
    public ResponseEntity<Map<String, String>> addBookDetails(@RequestBody BookDetails bookDetails) {
        return bookDetailsService.addNewBook(bookDetails);
    }

    @GetMapping("/bookDetails/searchName")
    public List<BookDetails> searchBookByNameQuery(@Param("name") String name) {
    return bookDetailsService.searchBookByName(name);
    }
    @PutMapping("/bookDetails/delete/{bookCode}")
    public ResponseEntity<Map<String,String>> deleteBook(@PathVariable long bookCode){
return bookDetailsService.deleteBook(bookCode);
    }

}
