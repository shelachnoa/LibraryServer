package com.example.myLibraryServer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import com.example.myLibraryServer.model.Book;
import com.example.myLibraryServer.model.Reader;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
@Table(name="borrowing")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "readerId",nullable = false)
    @JsonProperty("readerId")
    private Reader readerId;
    @ManyToOne
    @JoinColumn(name = "bookId",nullable = false)
    @JsonProperty("bookId")
    private Book bookId;
//    @Temporal(TemporalType.DATE)
    private Date borrowedDate;

    private Date returnedDate;
    @Transient
    private long delayDays;

    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public Reader getReaderId() {
        return readerId;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setDelayDays(long delayDays) {
        this.delayDays = delayDays;
    }
}
