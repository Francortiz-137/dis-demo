package com.disneydemo.demo.service.implementation;

import com.disneydemo.demo.model.Genre;
import com.disneydemo.demo.model.Movie;
import com.disneydemo.demo.repository.GenreRepository;
import com.disneydemo.demo.repository.MovieRepository;
import com.disneydemo.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    GenreRepository genreRepository;

    @Override
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> getMovie() {
        return null;
    }

    @Override
    public Movie updateMovie(Movie movie) {
        return null;
    }

    @Override
    public boolean existMovie(Long id) {
        return false;
    }

    @Override
    public Optional<Movie> findById(Long id) {

        return movieRepository.findById(id);

    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
        return;
    }

    @Override
    public Movie findByTitle(String s) {
        return movieRepository.findByTitle(s);
    }

    @Override
    public List<Movie> findByGenre(Long idGenre) {
        Genre genre = genreRepository.findById(idGenre).get();
        return movieRepository.findByGenre(genre);
    }


    @Override
    public List<Movie> findByCreationDate(String s) {
        return null;
    }

    @Override
    public List<Movie> findAllOrderByCreationDateAsc() {
        return movieRepository.findAllByOrderByCreationDateAsc();
    }

    @Override
    public List<Movie> findAllOrderByCreationDateDesc() {
        return movieRepository.findAllByOrderByCreationDateDesc();
    }
}
