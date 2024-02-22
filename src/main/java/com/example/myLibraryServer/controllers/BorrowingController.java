package com.example.myLibraryServer.controllers;

import com.example.myLibraryServer.model.Book;
import com.example.myLibraryServer.model.Borrowing;
import com.example.myLibraryServer.model.Reader;
import com.example.myLibraryServer.repo.BookRepo;
import com.example.myLibraryServer.repo.BorrowingRepo;
import com.example.myLibraryServer.services.BorrowingService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class BorrowingController {
    @Autowired
    BorrowingRepo borrowingRepo;
    @Autowired
    BookRepo bookRepo;
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    BorrowingService borrowingService;

    @GetMapping("/borrowings")
    public List<Borrowing> getBorrowings() {
        return borrowingService.getAllBorrowings();
    }

    @GetMapping("/borrowings/archive")
    public List<Borrowing> getOldBorrowings() {
        return borrowingService.getOldBorrowings();
    }

    @PostMapping("/borrowings/addBorrowing")
    public ResponseEntity<Map<String, String>> addBorrowing(@RequestBody Borrowing borrowing) {
       return  borrowingService.addBorrowing(borrowing);
    }

    @PutMapping("/borrowings/{borrowId}")
    public ResponseEntity<Map<String, String>> updateReturnedDate(@PathVariable Long borrowId) {
        return  borrowingService.updateReturnDate(borrowId);
    }
    @GetMapping("borrowings/topTen")
    public List<Object[]> topTen() {
   return borrowingService.topTen();
    }
    @GetMapping("/borrowings/searchByBookCode/{bookCode}")

    public List<Borrowing> getBorrowingByBookCode(@PathVariable long bookCode){
        System.out.println(bookCode);
        return borrowingService.searchBorrowingByBookCode(bookCode);
    }

    @GetMapping("/borrowings/searchByReaderName/{readerName}")
    public List<Borrowing> getBorrowingsByReaderName(@PathVariable String readerName){
        return borrowingService.findBorrowingsByReaderName(readerName);
    }

    @GetMapping("/borrowings/searchByBorrowDate/{borrowedDate}")
    public List<Borrowing> getBorrowingsByBorrowDate(@PathVariable  @DateTimeFormat(pattern = "yyyy-MM-dd") Date borrowedDate){
        List<Borrowing> x=borrowingService.findBorrowingsByBorrowDate(borrowedDate);
        return x ;
    }
    @GetMapping("/borrowings/searchByReaderId/{readerId}")
    public List<Borrowing> getBorrowingsByBorrowDate(@PathVariable long readerId){
return borrowingService.getBorrowingsByReaderId(readerId);
    }

}
