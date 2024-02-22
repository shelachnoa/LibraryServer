package com.example.myLibraryServer.model;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.text.SimpleDateFormat;


@Data
@Entity
@Table(name="reader")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(nullable = false)
    @JsonProperty("fullName")
    private String fullName;
    @Column( nullable = false)
    @JsonProperty("email")
    private String email;
    private boolean deleted;

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
