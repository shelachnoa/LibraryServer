package com.example.myLibraryServer.repo;

import com.example.myLibraryServer.model.BookDetails;
import com.example.myLibraryServer.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface PublisherRepo extends JpaRepository<Publisher,Long> {
    List<Publisher> findByDeletedFalse();
}
