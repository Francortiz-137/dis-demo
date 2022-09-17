package com.disneydemo.demo.controller;

import com.disneydemo.demo.DTO.DTO;
import com.disneydemo.demo.model.Character;
import com.disneydemo.demo.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
            //@RequestBody Character character,
            @RequestParam String name,
            @RequestParam String img,
            @RequestParam int age,
            @RequestParam Double weight,
            @RequestParam String story,
            Authentication authentication
    ){
        if(AuthController.isGuest(authentication)){
            return new ResponseEntity<>(DTO.makeMap("error","Not Authorized"), HttpStatus.UNAUTHORIZED);
        }else {
            Character newCharacter = characterService.saveCharacter(new Character(img,name,age,weight,story));
            return new ResponseEntity<>(DTO.characterToDTO(newCharacter), HttpStatus.CREATED);
        }

    }

    @RequestMapping(path = "/characters/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateCharacter(@PathVariable Optional<Long> id,
            //@RequestBody Character character,
                                                  @RequestParam Optional<String> name,
                                                  @RequestParam Optional<String> img,
                                                  @RequestParam Optional<Integer> age,
                                                  @RequestParam Optional<Double> weight,
                                                  @RequestParam Optional<String> story,
            Authentication authentication)
    {
        if(AuthController.isGuest(authentication)){
            return new ResponseEntity<>(DTO.makeMap("error","Not Authorized"), HttpStatus.UNAUTHORIZED);
        }else {
            if (id.isEmpty()) return new ResponseEntity<>(DTO.makeMap("error","id is required"), HttpStatus.ACCEPTED);

            Character updatedCharacter = characterService.findById(id.get());
            updatedCharacter.setId(id.get());
            img.ifPresent(updatedCharacter::setImg);
            name.ifPresent(updatedCharacter::setName);
            age.ifPresent(updatedCharacter::setAge);
            weight.ifPresent(updatedCharacter::setWeight);
            story.ifPresent(updatedCharacter::setStory);
            return new ResponseEntity<>(DTO.characterToDTO(characterService.saveCharacter(updatedCharacter)), HttpStatus.ACCEPTED);
        }
    }

    @RequestMapping(path = "/characters/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCharacter(
            @PathVariable("id") Long id,
            Authentication authentication
    ){
        if(AuthController.isGuest(authentication)){
            return new ResponseEntity<>(DTO.makeMap("error","Not Authorized"), HttpStatus.UNAUTHORIZED);
        }else {
            Character character = characterService.deleteCharacter(id);
            return new ResponseEntity<>(DTO.characterToDTO(character), HttpStatus.OK);
        }
    }


    @RequestMapping(path = "/characters", method = RequestMethod.GET)
    public ResponseEntity<Object> findCharacter(
            @RequestParam Optional<String> name,
            @RequestParam Optional<Integer> age,
            @RequestParam Optional<Long> idMovie,
            Authentication authentication
    ){

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

    @RequestMapping(path = "/characters/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> findCharacter(
            @PathVariable Long id,
            Authentication authentication
    ){
        Character character = characterService.findById(id);

        if (character == null) {
            return new ResponseEntity<>(DTO.makeMap("error","Not Found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(DTO.characterToDTO(character), HttpStatus.FOUND);
    }
}
