package com.example.myLibraryServer.services;

import com.example.myLibraryServer.model.Book;
import com.example.myLibraryServer.model.BookDetails;
import com.example.myLibraryServer.repo.BookDetailsRepo;
import com.example.myLibraryServer.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookDetailsService {
    @Autowired
    BookDetailsRepo bookDetailsRepo;
    @Autowired
    BookService bookService;

    public List<BookDetails> getBooksDetails(){
        return bookDetailsRepo.findByNumOfCopiesGreaterThanOrderByBookNameAsc(0);
    }
    public ResponseEntity<Map<String,String>> addNewBook(BookDetails bookDetails){
        try{
            BookDetails savedBookDetails= bookDetailsRepo.save(bookDetails);
               bookService.addBooks(bookDetails.getNumOfCopies(),savedBookDetails);


            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","Book Details added successfully"));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message","Failed to add Book Details"));
        }
    }

    public List<BookDetails> searchBookByName(String bookName){
        return bookDetailsRepo.findByBookNameIgnoreCaseContainingAndNumOfCopiesGreaterThan(bookName,0);
    }

    public ResponseEntity<Map<String,String>> deleteBook(long bookCode){
        List<Book> bookCopy=bookService.getCopiesOfBook(bookCode);
       return bookService.deleteBook(bookCopy.get(0).getId());
    }
}
