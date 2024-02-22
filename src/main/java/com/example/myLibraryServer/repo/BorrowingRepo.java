package com.example.myLibraryServer.repo;

import com.example.myLibraryServer.model.Book;
import com.example.myLibraryServer.model.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RepositoryRestResource
public interface BorrowingRepo extends JpaRepository<Borrowing,Long> {
    List<Borrowing> findByReturnedDateIsNullOrderByBorrowedDateDesc();
    List<Borrowing> findByReturnedDateIsNotNull();
List<Borrowing> findByBookId_IdAndReturnedDateIsNull(long bookId);
    List<Borrowing> findByReaderIdFullNameIgnoreCaseContainingAndReturnedDateIsNull(String readerName);

    List<Borrowing> findByReaderId_IdAndReturnedDateIsNull(Long readerId);
//    @Query("SELECT b FROM Borrowing b WHERE DATE(b.borrowedDate) = :borrowedDate AND b.returnedDate IS NULL")
//List<Borrowing> findByBorrowedDateAndReturnedDateIsNull(Date borrowedDate);

    List<Borrowing> findByReturnedDateIsNullAndBorrowedDate(Date borrowedDate);
//    List<Object[]> findTop10BooksByOrderByBookIdDesc();

    @Query("SELECT b.bookId, COUNT(b.bookId) " +
            "FROM Borrowing b " +
            "GROUP BY b.bookId " +
            "ORDER BY COUNT(b.bookId) DESC "+
    "LIMIT 10")
    List<Object[]> findTopTenBooks();
}

