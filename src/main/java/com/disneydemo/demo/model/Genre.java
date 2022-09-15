package com.disneydemo.demo.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String img;

    private String name;

    @OneToMany(mappedBy="genre", orphanRemoval = true, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Movie> movies = new LinkedList<Movie>();

    public Genre(String img, String name, List<Movie> series) {
        this.img = img;
        this.name = name;
        this.movies = series;
    }

    public List<Movie> getSeries() {
        return movies;
    }

    public void setSeries(List<Movie> movies) {
        this.movies = movies;
    }

    public Genre(String img, String name) {
        this.img = img;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Genre() {
    }


    public void addMovie(Movie movie) {
        this.movies.add(movie);
        movie.setGenre(this);
    }
}
