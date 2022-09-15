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
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

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

            @RequestBody Movie movie,
            @RequestParam("genreId") Long genreId
    ){
        /*if(Util.isGuest(authentication)){
            return new ResponseEntity<>(Util.makeMap("error","Not Authorized"), HttpStatus.UNAUTHORIZED);
        }else {
            Game game = gameService.saveGame(new Game(LocalDateTime.now()));
            GamePlayer gamePlayer = gamePlayerService.saveGamePlayer(new GamePlayer(game, playerService.findByUserName(authentication.getName()),LocalDateTime.now()));
            return new ResponseEntity<>((Util.makeMap("gpid",gamePlayer.getId())),HttpStatus.CREATED);
        }

    TODO agregar lista de personajes
         */

        Genre genre = genreService.findById(genreId);
        genre.addMovie(movie);
        movie.setGenre(genre);

        return new ResponseEntity<>(DTO.MovieToDTO(movieService.saveMovie(movie)), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/movies", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateMovies(@RequestParam Long id,
                                               @RequestParam Long genreId,
                                               @RequestBody Movie movie)
    {

        Movie updatedMovie = movieService.findById(id);
        updatedMovie.setImg(movie.getImg());
        updatedMovie.setTitle(movie.getTitle());
        updatedMovie.setScore(movie.getScore());
        updatedMovie.setCreationDate(movie.getCreationDate());
        updatedMovie.setGenre(genreService.findById(genreId));

        return new ResponseEntity<>(DTO.MovieToDTO(movieService.saveMovie(updatedMovie)), HttpStatus.ACCEPTED);
    }

    @RequestMapping(path = "/movies/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteMovie(
            @PathVariable Long id
    ){
        /*if(Util.isGuest(authentication)){
            return new ResponseEntity<>(Util.makeMap("error","Not Authorized"), HttpStatus.UNAUTHORIZED);
        }else {
            Game game = gameService.saveGame(new Game(LocalDateTime.now()));
            GamePlayer gamePlayer = gamePlayerService.saveGamePlayer(new GamePlayer(game, playerService.findByUserName(authentication.getName()),LocalDateTime.now()));
            return new ResponseEntity<>((Util.makeMap("gpid",gamePlayer.getId())),HttpStatus.CREATED);
        }

         */
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
            @RequestParam Optional<String> order
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

    @RequestMapping(path = "/movies/id", method = RequestMethod.GET)
    public ResponseEntity<Object> findMovie(
            @RequestParam Long id
    ){
        return new ResponseEntity<>(DTO.MovieToDTO(movieService.findById(id)), HttpStatus.FOUND);
    }

    @RequestMapping(path = "/movies/{idMovie}/characters/{idCharacter}", method = RequestMethod.POST)
    public ResponseEntity<Object> addCharacterToMovie(
            @PathVariable("idMovie") Long idMovie,
            @PathVariable("idCharacter") Long idCharacter
    ){

        Character character = characterService.findById(idCharacter);
        Movie movie = movieService.findById(idMovie);

        CharacterMovie characterMovie = new CharacterMovie(character,movie);
        movie.addCharacterMovie(characterMovie);
        character.addCharacterMovie(characterMovie);

        movie = movieService.saveMovie(movie);
        characterService.saveCharacter(character);


        characterSerieService.saveCharacterSerie(characterMovie);

        return new ResponseEntity<>(DTO.CharacterMovieToDTO(movie), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/movies/{idMovie}/characters/{idCharacter}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCharacterFromMovie(
            @PathVariable("idMovie") Long idMovie,
            @PathVariable("idCharacter") Long idCharacter
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
}
