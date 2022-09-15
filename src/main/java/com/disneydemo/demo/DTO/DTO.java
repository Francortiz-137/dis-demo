package com.disneydemo.demo.DTO;

import com.disneydemo.demo.model.*;
import com.disneydemo.demo.model.Character;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class DTO {

    public static Map<String, Object> characterToDTO(Character character) {
        /** Return character's information
         *
         */
        /*
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("created", game.getGameDate());
        dto.put("gamePlayers", game.getGamePlayers().stream()
                .map(gamePlayer -> DTO.gamePlayersToDTO(gamePlayer))
                .collect(toList()));

        dto.put("scores",   game.getGamePlayers().stream()
                .map(gp -> DTO.scoresToDTO(gp))
                .collect(toList()));
        return dto;

         */
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", character.getId());
        dto.put("img", character.getImg());
        dto.put("name", character.getName());

        return dto;

    }

    public static Map<String, Object> serieToDTO(Movie movie) {

        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", movie.getId());

        return dto;
    }

    public static Map<String, Object> genreToDTO(Genre genre) {

        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", genre.getId());

        return dto;
    }

    public static Map<String, Object> characterSerieToDTO(CharacterMovie characterMovie) {

        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", characterMovie.getId());

        return dto;
    }

    public static Map<String, Object> charactersToDTO(List<Character> characters) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        // TODO solo mostrar img y name
        dto.put("characters", characters.stream().map(character -> characterToDTO(character)));
        return dto;
    }

    public static Object MovieToDTO(Movie movie) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("title", movie.getTitle());
        dto.put("img", movie.getImg());
        dto.put("date",movie.getCreationDate());
        dto.put("score",movie.getScore());
        return dto;
    }

    public static Object MoviesToDTO(List<Movie> movies) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();

        if (movies != null) {
            dto.put("movies", movies.stream()
                    .map(DTO::MovieToDTO)
                    .collect(toList()));
        }
        return dto;
    }

    public static Object CharacterMovieToDTO(Movie movie) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("title", movie.getTitle());
        dto.put("date",movie.getCreationDate());
        dto.put("score",movie.getScore());
        dto.put("characters",movie.getCharacterMovies().stream()
                        .map(characterMovie -> DTO.characterToDTO(characterMovie.getCharacter()))
                        .collect(toList())
        );


        return dto;
    }

    public static Object makeMap(String key, String value) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put(key,value);
        return dto;
    }

    public static Object usersToDTO(List<DisneyUser> disneyUsers) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("disneyUsers", disneyUsers.stream()
                .map(DTO::userToDTO)
                .collect(toList()));
        return dto;
    }

    public static Object userToDTO(DisneyUser disneyUser) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("userName", disneyUser.getUserName());
        dto.put("email", disneyUser.getEmail());
        return dto;
    }
}
