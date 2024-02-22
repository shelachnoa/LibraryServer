package com.example.myLibraryServer.repo;
import com.example.myLibraryServer.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ReaderRepo extends JpaRepository<Reader,Long> {
    List<Reader> findByDeletedFalse();

}
