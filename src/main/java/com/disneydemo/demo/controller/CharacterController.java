package com.disneydemo.demo.controller;

import com.disneydemo.demo.DTO.DTO;
import com.disneydemo.demo.model.Character;
import com.disneydemo.demo.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

@RestController

@RequestMapping("/api")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @RequestMapping(path = "/characters", method = RequestMethod.POST)
    public ResponseEntity<Object> createCharacter(
            @RequestBody Character character
    ){
        /*if(Util.isGuest(authentication)){
            return new ResponseEntity<>(Util.makeMap("error","Not Authorized"), HttpStatus.UNAUTHORIZED);
        }else {
            Game game = gameService.saveGame(new Game(LocalDateTime.now()));
            GamePlayer gamePlayer = gamePlayerService.saveGamePlayer(new GamePlayer(game, playerService.findByUserName(authentication.getName()),LocalDateTime.now()));
            return new ResponseEntity<>((Util.makeMap("gpid",gamePlayer.getId())),HttpStatus.CREATED);
        }

         */
        Character newCharacter = characterService.saveCharacter(character);
        return new ResponseEntity<>(DTO.characterToDTO(newCharacter), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/characters/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateCharacter(@PathVariable Long id,
            @RequestBody Character character)
    {

        Character updatedCharacter = characterService.findById(id);
        updatedCharacter.setId(id);
        updatedCharacter.setImg(character.getImg());
        updatedCharacter.setName(character.getName());
        updatedCharacter.setAge(character.getAge());
        updatedCharacter.setWeight(character.getWeight());
        updatedCharacter.setStory(character.getStory());

        return new ResponseEntity<>(DTO.characterToDTO(characterService.saveCharacter(updatedCharacter)), HttpStatus.ACCEPTED);
    }

    @RequestMapping(path = "/characters/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCharacter(
            @PathVariable("id") Long id
    ){
        /*if(Util.isGuest(authentication)){
            return new ResponseEntity<>(Util.makeMap("error","Not Authorized"), HttpStatus.UNAUTHORIZED);
        }else {
            Game game = gameService.saveGame(new Game(LocalDateTime.now()));
            GamePlayer gamePlayer = gamePlayerService.saveGamePlayer(new GamePlayer(game, playerService.findByUserName(authentication.getName()),LocalDateTime.now()));
            return new ResponseEntity<>((Util.makeMap("gpid",gamePlayer.getId())),HttpStatus.CREATED);
        }

         */
        Character character = characterService.deleteCharacter(id);
        return new ResponseEntity<>(DTO.characterToDTO(character), HttpStatus.OK);
    }


    @RequestMapping(path = "/characters", method = RequestMethod.GET)
    public ResponseEntity<Object> findCharacter(
            @RequestParam Optional<String> name,
            @RequestParam Optional<Integer> age,
            @RequestParam Optional<Long> idMovie
    ){
        /*if(Util.isGuest(authentication)){
            return new ResponseEntity<>(Util.makeMap("error","Not Authorized"), HttpStatus.UNAUTHORIZED);
        }else {
            Game game = gameService.saveGame(new Game(LocalDateTime.now()));
            GamePlayer gamePlayer = gamePlayerService.saveGamePlayer(new GamePlayer(game, playerService.findByUserName(authentication.getName()),LocalDateTime.now()));
            return new ResponseEntity<>((Util.makeMap("gpid",gamePlayer.getId())),HttpStatus.CREATED);
        }

         */
        if (name.isPresent()) {
            return new ResponseEntity<>(DTO.characterToDTO(characterService.findByName(name.get())), HttpStatus.FOUND);
        }
        else if (age.isPresent()) {
            return new ResponseEntity<>(DTO.charactersToDTO(characterService.findByAge(age.get())), HttpStatus.FOUND);
        } else if (idMovie.isPresent()) {
            return new ResponseEntity<>(DTO.charactersToDTO(characterService.findByMovie(idMovie.get())), HttpStatus.FOUND);
        } else{
            return new ResponseEntity<>(DTO.charactersToDTO(characterService.findAll()), HttpStatus.FOUND);
        }
    }
}
