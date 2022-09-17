package com.disneydemo.demo.controller;

import com.disneydemo.demo.DTO.DTO;
import com.disneydemo.demo.model.*;
import com.disneydemo.demo.model.Character;
import com.disneydemo.demo.model.Movie;
import com.disneydemo.demo.repository.GenreRepository;
import com.disneydemo.demo.service.CharacterSerieService;
import com.disneydemo.demo.service.CharacterService;
import com.disneydemo.demo.service.GenreService;
import com.disneydemo.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.util.stream.Collectors.toList;

@RestController

@RequestMapping("/api")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    CharacterSerieService characterSerieService;



    @RequestMapping(path = "/movies", method = RequestMethod.POST)
    public ResponseEntity<Object> createMovie(
            //@RequestBody Movie movie,
            @RequestParam String title,
            @RequestParam String img,
            @RequestParam Double score,
            @RequestParam Date creationDate,
            @RequestParam("genreId") Long genreId,
            @RequestParam("characters") List<Long> charactersId,
            Authentication authentication
    ){

        if (score<0 || score<5){
            new ResponseEntity<>(DTO.makeMap("error","Please set the score with a value between 1 to 5"), HttpStatus.BAD_REQUEST);
        }

        Movie movie = new Movie(img,title,creationDate,score);
        Genre genre = genreService.findById(genreId);
        genre.addMovie(movie);
        movie.setGenre(genre);


        List<Character> characters = characterService.findAllById(charactersId);
        List<CharacterMovie> characterMovies = new ArrayList<CharacterMovie>();

        characters.forEach(character -> {
           createCharacterMovie(character,movie);
        });


        return new ResponseEntity<>(DTO.MovieToDTO(movie), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/movies", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateMovies(@RequestParam Optional<Long> id,
                                               @RequestParam Optional<Long> genreId,
                                               @RequestParam Optional<String> title,
                                               @RequestParam Optional<String> img,
                                               @RequestParam Optional<Double> score,
                                               @RequestParam Optional<Date> creationDate,
                                               //@RequestBody Movie movie,
                                               Authentication authentication)
    {
        if (id.isEmpty()) return new ResponseEntity<>(DTO.makeMap("error","id is required"), HttpStatus.BAD_REQUEST);

        Movie updatedMovie = movieService.findById(id.get());

        img.ifPresent(updatedMovie::setImg);
        title.ifPresent(updatedMovie::setTitle);
        score.ifPresent(updatedMovie::setScore);
        creationDate.ifPresent(updatedMovie::setCreationDate);
        genreId.ifPresent(aLong -> updatedMovie.setGenre(genreService.findById(aLong)));

        return new ResponseEntity<>(DTO.MovieToDTO(movieService.saveMovie(updatedMovie)), HttpStatus.ACCEPTED);
    }

    @RequestMapping(path = "/movies/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteMovie(
            @PathVariable Long id,
            Authentication authentication
    ){
        if (movieService.findById(id) == null) {
            return new ResponseEntity<>("Not Found", HttpStatus.OK);
        }
        else{
            movieService.deleteMovie(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
    }


    @RequestMapping(path = "/movies", method = RequestMethod.GET)
    public ResponseEntity<Object> findMovie(
            @RequestParam Optional<String> title,
            @RequestParam Optional<Long> genre,
            @RequestParam Optional<String> order,
            Authentication authentication
    ){

        if (title.isPresent()) {
            return new ResponseEntity<>(DTO.MovieToDTO(movieService.findByTitle(title.get())), HttpStatus.FOUND);
        }  else if (genre.isPresent()) {
            return new ResponseEntity<>(DTO.MoviesToDTO(movieService.findByGenre(genre.get())), HttpStatus.FOUND);
        } else if (order.isPresent()) {
            if(order.get().equals("ASC"))
                return new ResponseEntity<>(DTO.MoviesToDTO(movieService.findAllOrderByCreationDateAsc()), HttpStatus.FOUND);
            if(order.get().equals("DESC"))
                return new ResponseEntity<>(DTO.MoviesToDTO(movieService.findAllOrderByCreationDateDesc()), HttpStatus.FOUND);
            else
                return new ResponseEntity<>(DTO.MoviesToDTO(movieService.findAll()), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(DTO.MoviesToDTO(movieService.findAll()), HttpStatus.FOUND);
        }
    }

    @RequestMapping(path = "/movies/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> detailMovie(
            @PathVariable Long id,
            Authentication authentication
    ){
        Movie movie = movieService.findById(id);

        if (movie == null) {
            return new ResponseEntity<>(DTO.makeMap("error","Not Found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(DTO.MovieToDTO(movie), HttpStatus.FOUND);
    }

    @RequestMapping(path = "/movies/{idMovie}/characters/{idCharacter}", method = RequestMethod.POST)
    public ResponseEntity<Object> addCharacterToMovie(
            @PathVariable("idMovie") Long idMovie,
            @PathVariable("idCharacter") Long idCharacter,
            Authentication authentication
    ){

        Character character = characterService.findById(idCharacter);
        Movie movie = movieService.findById(idMovie);

        return new ResponseEntity<>(DTO.CharacterMovieToDTO(createCharacterMovie(character,movie)), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/movies/{idMovie}/characters/{idCharacter}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCharacterFromMovie(
            @PathVariable("idMovie") Long idMovie,
            @PathVariable("idCharacter") Long idCharacter,
            Authentication authentication
    ){

        Character character = characterService.findById(idCharacter);
        Movie movie = movieService.findById(idMovie);


        CharacterMovie characterMovie =  movie.getCharacterMovies().stream().filter(
                  cm -> cm.getCharacter().getId()==character.getId()
        ).findFirst().get();

        characterSerieService.deleteCharacterSerie(characterMovie.getId());

        movie.removeCharacterMovie(characterMovie);
        character.removeCharacterMovie(characterMovie);

        return new ResponseEntity<>(DTO.CharacterMovieToDTO(movie), HttpStatus.CREATED);
    }

    private Movie createCharacterMovie(Character character, Movie movie){



        CharacterMovie characterMovie = new CharacterMovie(character,movie);
        movie.addCharacterMovie(characterMovie);
        character.addCharacterMovie(characterMovie);

        movie = movieService.saveMovie(movie);
        characterService.saveCharacter(character);

        characterSerieService.saveCharacterSerie(characterMovie);
        return movie;
    }

}
