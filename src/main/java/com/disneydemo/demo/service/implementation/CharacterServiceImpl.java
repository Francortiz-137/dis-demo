package com.disneydemo.demo.service.implementation;

import com.disneydemo.demo.model.Character;
import com.disneydemo.demo.model.CharacterMovie;
import com.disneydemo.demo.model.Movie;
import com.disneydemo.demo.repository.CharacterRepository;
import com.disneydemo.demo.repository.MovieRepository;
import com.disneydemo.demo.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class CharacterServiceImpl implements CharacterService {
    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    MovieRepository movieRepository;

    @Override
    public Character saveCharacter(Character character){ return characterRepository.save(character);};

    @Override
    public List<Character> getCharacter(){ return null;};

    @Override
    public boolean existCharacter(Long id) { return characterRepository.findById(id).isPresent(); };

    @Override
    public Optional<Character> findById(Long id){ return characterRepository.findById(id); };

    @Override
    public List<Character> findAll(){ return characterRepository.findAll(); };

    @Override
    public Optional<Character> deleteCharacter(Long id)
    {
        Optional<Character> character = characterRepository.findById(id);
        characterRepository.deleteById(id);
        return character;
    }

    @Override
    public Character findByName(String name) {
        return characterRepository.findByName(name).get();
    }

    @Override
    public List<Character> findByAge(Integer s) {
        return characterRepository.findByAge(s);
    }

    @Override
    public List<Character> findByMovie(Long idMovie) {
        Movie movie = movieRepository.findById(idMovie).get();
        return movie.getCharacterMovies().stream().map(CharacterMovie::getCharacter).collect(toList());

    }

    @Override
    public List<Character> findAllById(List<Long> charactersId) {
        return characterRepository.findAllById(charactersId);
    }

}
