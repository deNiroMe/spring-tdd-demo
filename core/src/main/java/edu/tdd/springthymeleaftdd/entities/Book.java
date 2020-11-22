package edu.tdd.springthymeleaftdd.entities;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 500)
    private String summary;

    private String genre;

    public Book(String title, String summary, String genre) {
        this.title = title;
        this.summary = summary;
        this.genre = genre;
    }
}
