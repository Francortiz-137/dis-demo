package com.disneydemo.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String img;

    private String title;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date creationDate;

    private double score;

    @OneToMany(mappedBy="movie", orphanRemoval = true, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CharacterMovie> characterMovies = new LinkedList<CharacterMovie>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="genre_id")
    private Genre genre;

    public List<CharacterMovie> getCharacterMovies() {
        return characterMovies;
    }

    public void setCharacterMovies(List<CharacterMovie> characterMovies) {
        this.characterMovies = characterMovies;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Movie() {
    }

    public Movie(String img, String title, Date creationDate, double score) {
        this.img = img;
        this.title = title;
        this.creationDate = creationDate;
        this.score = score;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void addCharacterMovie(CharacterMovie characterMovie) {
        characterMovies.add(characterMovie);
    }

    public void removeCharacterMovie(CharacterMovie characterMovie) {
        characterMovies.remove(characterMovie);
    }
}
