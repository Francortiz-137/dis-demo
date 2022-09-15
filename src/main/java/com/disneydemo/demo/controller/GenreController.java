package com.disneydemo.demo.controller;

import com.disneydemo.demo.DTO.DTO;
import com.disneydemo.demo.model.Genre;
import com.disneydemo.demo.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/api")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @RequestMapping(path = "/genres", method = RequestMethod.POST)
    public ResponseEntity<Object> createGenre(
            @RequestParam String img, @RequestParam String name
    ){

        Genre genre = genreService.saveGenre(new Genre(img, name));
        return new ResponseEntity<>(DTO.genreToDTO(genre), HttpStatus.CREATED);
    }
}
