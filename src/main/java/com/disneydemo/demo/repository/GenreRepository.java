package com.disneydemo.demo.repository;

import com.disneydemo.demo.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface GenreRepository extends JpaRepository<Genre, Long> {

    List<Genre> findAll();

    Optional<Genre> findById(Long id);

    void deleteById(Long id);
}
