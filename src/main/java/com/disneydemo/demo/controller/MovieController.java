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
            @RequestParam Optional<String> title,
            @RequestParam Optional<String> img,
            @RequestParam Optional<Double> score,
            @RequestParam Optional<Date> creationDate,
            @RequestParam("genreId") Optional<Long> genreId,
            @RequestParam("characters") Optional<List<Long>> charactersId,
            Authentication authentication
    ){
        if(AuthController.isGuest(authentication))
            return new ResponseEntity<>(DTO.makeMap("error","Not Authorized"), HttpStatus.UNAUTHORIZED);
        if(title.isEmpty()||img.isEmpty()||score.isEmpty()|| creationDate.isEmpty()||genreId.isEmpty())
            return new ResponseEntity<>(DTO.makeMap("error","Please send the required values"), HttpStatus.BAD_REQUEST);
        if (score.get() < 0 || score.get() < 5){
            new ResponseEntity<>(DTO.makeMap("error","Please set the score with a value between 1 to 5"), HttpStatus.BAD_REQUEST);
        }
        Movie movie = new Movie(img.get(),title.get(),creationDate.get(),score.get());
        Genre genre = genreService.findById(genreId.get());
        genre.addMovie(movie);
        movie.setGenre(genre);

        List<Character> characters;
        List<CharacterMovie> characterMovies;

        if (charactersId.isEmpty())
            characters = new ArrayList<>();
        else {
            characters = characterService.findAllById(charactersId.get());
            characterMovies = new ArrayList<CharacterMovie>();
        }

        if (characters.isEmpty()){
            return new ResponseEntity<>(DTO.MovieToDTO(movieService.saveMovie(movie)), HttpStatus.CREATED);
        }

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
        if(AuthController.isGuest(authentication))
            return new ResponseEntity<>(DTO.makeMap("error","Not Authorized"), HttpStatus.UNAUTHORIZED);
        if (id.isEmpty()) return new ResponseEntity<>(DTO.makeMap("error","id is required"), HttpStatus.BAD_REQUEST);

        Optional<Movie> updatedMovie = movieService.findById(id.get());

        img.ifPresent(updatedMovie.get()::setImg);
        title.ifPresent(updatedMovie.get()::setTitle);
        score.ifPresent(updatedMovie.get()::setScore);
        creationDate.ifPresent(updatedMovie.get()::setCreationDate);
        genreId.ifPresent(aLong -> updatedMovie.get().setGenre(genreService.findById(aLong)));

        return new ResponseEntity<>(DTO.MovieToDTO(movieService.saveMovie(updatedMovie.get())), HttpStatus.ACCEPTED);
    }

    @RequestMapping(path = "/movies/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteMovie(
            @PathVariable Long id,
            Authentication authentication
    ){
        if(AuthController.isGuest(authentication))
            return new ResponseEntity<>(DTO.makeMap("error","Not Authorized"), HttpStatus.UNAUTHORIZED);
        if (movieService.findById(id).isEmpty()) {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
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
        if(AuthController.isGuest(authentication))
            return new ResponseEntity<>(DTO.makeMap("error","Not Authorized"), HttpStatus.UNAUTHORIZED);

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
            @PathVariable Optional<Long> id,
            Authentication authentication
    ){
        if(AuthController.isGuest(authentication))
            return new ResponseEntity<>(DTO.makeMap("error","Not Authorized"), HttpStatus.UNAUTHORIZED);
        if (id.isEmpty()) return new ResponseEntity<>(DTO.makeMap("error","id is required"), HttpStatus.BAD_REQUEST);

        Optional<Movie> movie = movieService.findById(id.get());

        if (movie.isEmpty())
            return new ResponseEntity<>(DTO.makeMap("error","Not Found"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(DTO.MovieToDTO(movie.get()), HttpStatus.FOUND);
    }

    @RequestMapping(path = "/movies/{idMovie}/characters/{idCharacter}", method = RequestMethod.POST)
    public ResponseEntity<Object> addCharacterToMovie(
            @PathVariable("idMovie") Optional<Long> idMovie,
            @PathVariable("idCharacter") Optional<Long> idCharacter,
            Authentication authentication
    ){
        if(AuthController.isGuest(authentication))
            return new ResponseEntity<>(DTO.makeMap("error","Not Authorized"), HttpStatus.UNAUTHORIZED);
        if (idMovie.isEmpty() || idCharacter.isEmpty()) return new ResponseEntity<>(DTO.makeMap("error","id is required"), HttpStatus.BAD_REQUEST);

        Optional<Character> character = characterService.findById(idCharacter.get());
        Optional<Movie> movie = movieService.findById(idMovie.get());

        if (character.isEmpty())
            return new ResponseEntity<>(DTO.makeMap("error","Character not found"), HttpStatus.NOT_FOUND);
        if (movie.isEmpty())
            return new ResponseEntity<>(DTO.makeMap("error","Movie not found"), HttpStatus.NOT_FOUND);


        return new ResponseEntity<>(DTO.MovieToDTO(createCharacterMovie(character.get(),movie.get()).getMovie()), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/movies/{idMovie}/characters/{idCharacter}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCharacterFromMovie(
            @PathVariable("idMovie") Optional<Long> idMovie,
            @PathVariable("idCharacter") Optional<Long> idCharacter,
            Authentication authentication
    ){
        if(AuthController.isGuest(authentication))
            return new ResponseEntity<>(DTO.makeMap("error","Not Authorized"), HttpStatus.UNAUTHORIZED);
        if (idMovie.isEmpty() || idCharacter.isEmpty()) return new ResponseEntity<>(DTO.makeMap("error","id is required"), HttpStatus.BAD_REQUEST);

        Optional<Character> character = characterService.findById(idCharacter.get());
        Optional<Movie> movie = movieService.findById(idMovie.get());

        if (character.isEmpty()||movie.isEmpty())
            return new ResponseEntity<>(DTO.makeMap("error","Not Found"), HttpStatus.NOT_FOUND);

        CharacterMovie characterMovie =  movie.get().getCharacterMovies().stream().filter(
                  cm -> cm.getCharacter().getId()==character.get().getId()
        ).findFirst().get();

        characterSerieService.deleteCharacterSerie(characterMovie.getId());

        movie.get().removeCharacterMovie(characterMovie);
        character.get().removeCharacterMovie(characterMovie);

        return new ResponseEntity<>(DTO.MovieToDTO(movie.get()), HttpStatus.OK);
    }

    private CharacterMovie createCharacterMovie(Character character, Movie movie){

        CharacterMovie characterMovie = new CharacterMovie(character,movie);
        movie.addCharacterMovie(characterMovie);
        character.addCharacterMovie(characterMovie);

        movieService.saveMovie(movie);
        characterService.saveCharacter(character);

        return characterSerieService.saveCharacterSerie(characterMovie);
    }

}
