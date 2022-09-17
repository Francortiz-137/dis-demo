package com.disneydemo.demo.DTO;

import com.disneydemo.demo.model.*;
import com.disneydemo.demo.model.Character;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class DTO {

    public static Map<String, Object> characterToDTO(Character character) {
        /** Return character's information detail
         *
         */

        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", character.getId());
        dto.put("img", character.getImg());
        dto.put("name", character.getName());
        dto.put("age",character.getAge());
        dto.put("weight",character.getWeight());
        dto.put("story",character.getStory());
        dto.put("movies",character.getCharacterMovies().stream().map(characterMovie -> characterSerieToDTO(characterMovie,"movie")).collect(toList()));
        return dto;

    }

    public static Map<String, Object> genreToDTO(Genre genre) {

        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("name", genre.getName());
        dto.put("img", genre.getImg());

        return dto;
    }

    public static Map<String, Object> characterSerieToDTO(CharacterMovie characterMovie,String option) {

        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        if (option.equals("movie"))
            dto.put("title",characterMovie.getMovie().getTitle());
        else if (option.equals("character")) {
            dto.put("name",characterMovie.getCharacter().getName());
        }
        return dto;
    }

    public static Map<String, Object> charactersToDTO(List<Character> characters) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        // TODO solo mostrar img y name
        dto.put("characters", characters.stream()
                .map(DTO::characterToDTO)
                .collect(toList()));
        return dto;
    }

    public static Object MovieToDTO(Movie movie) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("title", movie.getTitle());
        dto.put("img", movie.getImg());
        dto.put("date",movie.getCreationDate());
        dto.put("score",movie.getScore());
        dto.put("characters",movie.getCharacterMovies().stream()
                .map(characterMovie -> characterSerieToDTO(characterMovie,"character"))
                .collect(toSet()));
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

    public static Object genresToDTO(List<Genre> genres) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("genres", genres.stream()
                .map(DTO::genreToDTO)
                .collect(toList()));
        return dto;
    }
}
