package com.example.myLibraryServer.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;
    @ManyToOne
    @JoinColumn(name="bookCode")
    private BookDetails bookCode;
    private boolean isBorrowed;

    private boolean deleted;

    public boolean getIsBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public long getId() {
        return id;
    }

    public void setBookCode(BookDetails bookCode) {
        this.bookCode = bookCode;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public BookDetails getBookCode() {
        return bookCode;
    }
}
