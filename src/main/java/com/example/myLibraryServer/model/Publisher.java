package com.example.myLibraryServer.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="publisher")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;
    @Column(name = "name", nullable = false)
    @JsonProperty("name")
    private String name;
    private boolean deleted;

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}

