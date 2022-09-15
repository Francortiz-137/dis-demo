package com.disneydemo.demo.repository;

import com.disneydemo.demo.model.CharacterMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface CharacterSerieRepository extends JpaRepository<CharacterMovie, Long> {

    List<CharacterMovie> findAll();

    Optional <CharacterMovie> findById(Long id);

    void deleteById(Long id);
}
