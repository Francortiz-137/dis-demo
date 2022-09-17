package com.disneydemo.demo.service;

import com.disneydemo.demo.model.Character;

import java.util.List;
import java.util.Optional;

public interface CharacterService {

    Character saveCharacter(Character Character);

    List<Character> getCharacter();

    boolean existCharacter(Long id);

    Optional<Character> findById(Long id);

    List<Character> findAll();

    Optional<Character> deleteCharacter(Long id);

    Character findByName(String name);

    List<Character> findByAge(Integer s);

    List<Character> findByMovie(Long aLong);

    List<Character> findAllById(List<Long> charactersId);
}
