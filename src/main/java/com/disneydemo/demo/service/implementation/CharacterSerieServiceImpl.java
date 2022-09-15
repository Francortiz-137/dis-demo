package com.disneydemo.demo.service.implementation;

import com.disneydemo.demo.model.CharacterMovie;
import com.disneydemo.demo.repository.CharacterSerieRepository;
import com.disneydemo.demo.service.CharacterSerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterSerieServiceImpl implements CharacterSerieService {
    @Autowired
    CharacterSerieRepository characterSerieRepository;

    @Override
    public CharacterMovie saveCharacterSerie(CharacterMovie characterMovie){ return characterSerieRepository.save(characterMovie);};

    @Override
    public List<CharacterMovie> getCharacterSerie(){ return null;};

    @Override
    public CharacterMovie updateCharacterSerie(CharacterMovie CharacterMovie){ return null; };

    @Override
    public boolean existCharacterSerie(Long id) { return characterSerieRepository.findById(id).isPresent(); };

    @Override
    public CharacterMovie findById(Long id){ return null; };

    @Override
    public List<CharacterMovie> findAll(){ return characterSerieRepository.findAll(); };

    @Override
    public void deleteCharacterSerie(Long id){
        characterSerieRepository.deleteById(id);
        return;
    }
}
