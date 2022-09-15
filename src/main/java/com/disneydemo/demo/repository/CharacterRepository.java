package com.disneydemo.demo.repository;

import com.disneydemo.demo.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface CharacterRepository extends JpaRepository<Character, Long> {

    List<Character> findAll();

    Optional <Character> findById(Long id);

    void deleteById(Long id);

    Optional<Character> findByName(String name);

    List<Character> findByAge(Integer s);
}
