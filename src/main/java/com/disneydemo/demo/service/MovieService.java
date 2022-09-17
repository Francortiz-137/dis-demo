package com.disneydemo.demo.service;

import com.disneydemo.demo.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    Movie saveMovie(Movie movie);

    List<Movie> getMovie();

    Movie updateMovie(Movie movie);

    boolean existMovie(Long id);

    Optional<Movie> findById(Long id);

    List<Movie> findAll();

    void deleteMovie(Long id);

    Movie findByTitle(String s);

    List<Movie> findByGenre(Long l);

    List<Movie> findByCreationDate(String s);

    List<Movie> findAllOrderByCreationDateAsc();

    List<Movie> findAllOrderByCreationDateDesc();
}
