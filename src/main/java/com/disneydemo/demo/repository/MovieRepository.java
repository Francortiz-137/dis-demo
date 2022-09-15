package com.disneydemo.demo.repository;

import com.disneydemo.demo.model.Genre;
import com.disneydemo.demo.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findAll();

    Optional<Movie> findById(Long id);

    void deleteById(Long id);

    Movie findByTitle(String s);

    List<Movie> findAllByOrderByCreationDateAsc();

    List<Movie> findAllByOrderByCreationDateDesc();

    List<Movie> findByGenre(Genre idGenre);
}
