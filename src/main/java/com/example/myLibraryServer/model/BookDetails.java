package com.example.myLibraryServer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import com.example.myLibraryServer.model.Publisher;
@Data
@Entity
@Table(name="bookDetails")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "code")
    private long code;
    @JsonProperty("bookName")
    private String bookName;
    @JsonProperty("author")
    private String author;
    /*
    every publisher have several books
     */
    @ManyToOne
    @JoinColumn(name="publisherId",nullable = false)
    @JsonProperty("publisherId")
    private Publisher publisherId;
    @JsonProperty("price")
    @Column(nullable = false)
    private float price;
    @JsonProperty("category")
    @Column(nullable = false)
    private String category;
    @JsonProperty("image")
    private String image;
@Column(nullable = true, columnDefinition = "int default 0")
    private int numOfCopies;

    public long getCode() {
        return code;
    }
    public int getNumOfCopies() {
        return numOfCopies;
    }

    public void setNumOfCopies(int numOfCopies) {
        this.numOfCopies = numOfCopies;
    }
}
