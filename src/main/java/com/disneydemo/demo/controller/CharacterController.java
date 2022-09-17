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
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> img,
            @RequestParam Optional<Integer> age,
            @RequestParam Optional<Double> weight,
            @RequestParam Optional<String> story,
            Authentication authentication
    ){
        if(AuthController.isGuest(authentication))
            return new ResponseEntity<>(DTO.makeMap("error","Not Authorized"), HttpStatus.UNAUTHORIZED);
        if (name.isEmpty()||img.isEmpty()||age.isEmpty()||weight.isEmpty()||story.isEmpty())
            return new ResponseEntity<>(DTO.makeMap("error","Please send the required values"), HttpStatus.BAD_REQUEST);

        Character newCharacter = characterService.saveCharacter(new Character(img.get(),name.get(),age.get(),weight.get(),story.get()));
        return new ResponseEntity<>(DTO.characterToDTO(newCharacter), HttpStatus.CREATED);


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
        if(AuthController.isGuest(authentication))
            return new ResponseEntity<>(DTO.makeMap("error","Not Authorized"), HttpStatus.UNAUTHORIZED);

        if (id.isEmpty()) return new ResponseEntity<>(DTO.makeMap("error","id is required"), HttpStatus.BAD_REQUEST);

        Optional<Character> updatedCharacter = characterService.findById(id.get());

        if (updatedCharacter.isEmpty())
            return new ResponseEntity<>(DTO.makeMap("error","character not found"), HttpStatus.NOT_FOUND);

        updatedCharacter.get().setId(id.get());
        img.ifPresent(updatedCharacter.get()::setImg);
        name.ifPresent(updatedCharacter.get()::setName);
        age.ifPresent(updatedCharacter.get()::setAge);
        weight.ifPresent(updatedCharacter.get()::setWeight);
        story.ifPresent(updatedCharacter.get()::setStory);
        return new ResponseEntity<>(DTO.characterToDTO(characterService.saveCharacter(updatedCharacter.get())), HttpStatus.ACCEPTED);

    }

    @RequestMapping(path = "/characters/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCharacter(
            @PathVariable("id") Optional<Long> id,
            Authentication authentication
    ){
        if(AuthController.isGuest(authentication))
            return new ResponseEntity<>(DTO.makeMap("error","Not Authorized"), HttpStatus.UNAUTHORIZED);
        if (id.isEmpty())
            return new ResponseEntity<>(DTO.makeMap("error","Id is required"), HttpStatus.BAD_REQUEST);
        if(characterService.findById(id.get()).isEmpty())
            return new ResponseEntity<>(DTO.makeMap("error","Character not Found"), HttpStatus.NOT_FOUND);
        Optional<Character> character = characterService.deleteCharacter(id.get());
        return new ResponseEntity<>(DTO.characterToDTO(character.get()), HttpStatus.OK);

    }


    @RequestMapping(path = "/characters", method = RequestMethod.GET)
    public ResponseEntity<Object> findCharacter(
            @RequestParam Optional<String> name,
            @RequestParam Optional<Integer> age,
            @RequestParam Optional<Long> idMovie,
            Authentication authentication
    ){
        if(AuthController.isGuest(authentication))
            return new ResponseEntity<>(DTO.makeMap("error","Not Authorized"), HttpStatus.UNAUTHORIZED);

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
            @PathVariable Optional<Long> id,
            Authentication authentication
    ){
        if(AuthController.isGuest(authentication))
            return new ResponseEntity<>(DTO.makeMap("error","Not Authorized"), HttpStatus.UNAUTHORIZED);
        if (id.isEmpty()) return new ResponseEntity<>(DTO.makeMap("error","id is required"), HttpStatus.BAD_REQUEST);

        Optional<Character> character = characterService.findById(id.get());

        if (character.isEmpty()) {
            return new ResponseEntity<>(DTO.makeMap("error","Not Found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(DTO.characterToDTO(character.get()), HttpStatus.FOUND);
    }
}
