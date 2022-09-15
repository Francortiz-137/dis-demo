package com.disneydemo.demo.service;

import com.disneydemo.demo.model.Genre;

import java.util.List;


public interface GenreService {
    Genre saveGenre(Genre genre);

    List<Genre> getGenre();

    Genre updateGenre(Genre genre);

    boolean existGenre(Long id);

    Genre findById(Long id);

    List<Genre> findAll();

}
