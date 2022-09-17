package com.disneydemo.demo.service.implementation;

import com.disneydemo.demo.model.Genre;
import com.disneydemo.demo.repository.CharacterRepository;
import com.disneydemo.demo.repository.GenreRepository;
import com.disneydemo.demo.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    GenreRepository genreRepository;
    @Override
    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public List<Genre> getGenre() {
        return null;
    }

    @Override
    public Genre updateGenre(Genre genre) {
        return null;
    }

    @Override
    public boolean existGenre(Long id) {
        return false;
    }

    @Override
    public Genre findById(Long id) {
        return genreRepository.findById(id).get();
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }
}
