package com.example.myLibraryServer.services;

import com.example.myLibraryServer.model.Book;
import com.example.myLibraryServer.model.BookDetails;
import com.example.myLibraryServer.model.Borrowing;
import com.example.myLibraryServer.repo.BookRepo;
import com.example.myLibraryServer.repo.BorrowingRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BorrowingService {
    @Autowired
    BorrowingRepo borrowingRepo;
    @Autowired
    BookRepo bookRepo;
    @Autowired
    BookService bookService;
    @Autowired
    EntityManager entityManager;

    public List<Borrowing> getAllBorrowings() {
        List<Borrowing> openBorrowings=borrowingRepo.findByReturnedDateIsNullOrderByBorrowedDateDesc();
        openBorrowings.forEach(borrowing -> borrowing.setDelayDays(calculateDelayDays(borrowing)));
        return openBorrowings;

    }

    public List<Borrowing> getOldBorrowings() {
        return borrowingRepo.findByReturnedDateIsNotNull();
    }

    public ResponseEntity<Map<String, String>> addBorrowing(Borrowing borrowing) {
        try {

            borrowingRepo.save(borrowing);
            Optional<Book> existingBook = bookRepo.findById(borrowing.getBookId().getId()); //update the book as borrowed
            Book book = existingBook.get();
            if (book.getIsBorrowed())
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Book is already borrowed"));
            book.setBorrowed(true);
            bookRepo.save(book);
            LocalDate currentDate = LocalDate.now();
            borrowing.setBorrowedDate(java.sql.Date.valueOf(currentDate));
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Borrowing added successfully"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to add Borrowing details"));
        }
    }

    public ResponseEntity<Map<String, String>> updateReturnDate(long borrowId) {
        try {
            // Retrieve the existing borrowing from the database
            Optional<Borrowing> existingBorrowingOptional = borrowingRepo.findById(borrowId);

            if (existingBorrowingOptional.isPresent()) {
                Borrowing existingBorrowing = existingBorrowingOptional.get();
                LocalDate localDate = LocalDate.now();
                Date today = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                // Update the returned date in the existing borrowing
                existingBorrowing.setReturnedDate(today);
                existingBorrowing.getBookId().setBorrowed(false);
                // Save the updated borrowing
                Optional<Book> existingBook = bookRepo.findById(existingBorrowing.getBookId().getId()); //update the book as not borrowed
                Book book = existingBook.get();
                book.setBorrowed(false);
                bookRepo.save(book);
                borrowingRepo.save(existingBorrowing);

                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Returned date updated successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Borrowing with ID " + borrowId + " not found\""));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to update returned date"));
        }
    }

    public List<Borrowing> searchBorrowingByBookCode(long bookCode) {
        return borrowingRepo.findByBookId_IdAndReturnedDateIsNull(bookCode);

    }

    public List<Borrowing> findBorrowingsByReaderName(String readerName) {
        return borrowingRepo.findByReaderIdFullNameIgnoreCaseContainingAndReturnedDateIsNull(readerName);
    }

    public List<Borrowing> findBorrowingsByBorrowDate(Date borrowedDate) {
        List<Borrowing> x =  borrowingRepo.findByReturnedDateIsNullAndBorrowedDate(borrowedDate);
        return x;
    }

    public List<Object[]> topTen() {
        return borrowingRepo.findTopTenBooks();

    }

    public List<Borrowing> getBorrowingsByReaderId(long readerId){
       return borrowingRepo.findByReaderId_IdAndReturnedDateIsNull(readerId);
    }
    private long calculateDelayDays(Borrowing borrowing) {
        LocalDate borrowedLocalDate = borrowing.getBorrowedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();

        // Calculate the duration between borrowed date and current date
        Duration duration = Duration.between(borrowedLocalDate.atStartOfDay(), currentDate.atStartOfDay());

        // Convert duration to days
        long delayDays = duration.toDays();

        // Return the delay days if it's more than 14 days, otherwise return 0
        return delayDays > 14 ? delayDays - 14 : 0;
    }
}
