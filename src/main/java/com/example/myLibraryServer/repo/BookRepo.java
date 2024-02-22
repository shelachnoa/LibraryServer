package com.example.myLibraryServer.repo;

import com.example.myLibraryServer.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface BookRepo extends JpaRepository<Book, Long> {
    List<Book> findAllByDeletedFalse();

    List<Book> findAllByIsBorrowedFalseAndDeletedFalse();

    List<Book> findByBookCode_CodeAndDeletedIsFalse(long bookCode);
}
