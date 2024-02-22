package com.example.myLibraryServer.repo;

import com.example.myLibraryServer.model.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BookDetailsRepo extends JpaRepository<BookDetails,Long> {
    List<BookDetails> findAll();
//    List<BookDetails> findByPublisherId_Id(long publisherId);

    List<BookDetails> findByPublisherId_IdAndNumOfCopiesGreaterThan(Long publisherId, long numOfCopies);
//    List<BookDetails> findByBookNameIgnoreCaseContaining(String bookName);
    List<BookDetails> findByBookNameIgnoreCaseContainingAndNumOfCopiesGreaterThan(String bookName, int numOfCopies);

    List<BookDetails> findByNumOfCopiesGreaterThanOrderByBookNameAsc(long numOfCopies);


}
