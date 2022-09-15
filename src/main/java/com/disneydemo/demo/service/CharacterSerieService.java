package com.disneydemo.demo.service;

import com.disneydemo.demo.model.CharacterMovie;

import java.util.List;

public interface CharacterSerieService {

    CharacterMovie saveCharacterSerie(CharacterMovie CharacterMovie);

    List<CharacterMovie> getCharacterSerie();

    CharacterMovie updateCharacterSerie(CharacterMovie CharacterMovie);

    boolean existCharacterSerie(Long id);

    CharacterMovie findById(Long id);

    List<CharacterMovie> findAll();

    void deleteCharacterSerie(Long id);
}
