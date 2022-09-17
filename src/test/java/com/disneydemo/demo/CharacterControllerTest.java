package com.disneydemo.demo;

import com.disneydemo.demo.controller.CharacterController;
import com.disneydemo.demo.model.Character;
import com.disneydemo.demo.repository.CharacterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.ResponseEntity.status;

@RunWith(MockitoJUnitRunner.class)
public class CharacterControllerTest {
    /*
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private CharacterRepository characterRepository;

    @InjectMocks
    private CharacterController characterController;

    Character ch1 = new Character("imgWoody.jpg","Woody",0,0,"Woody is a cowboy toy from the movie Toy Story");
    Character ch2 = new Character("imgBuzz.jpg","Buzz Lightyear",0,0,"Buzz is an astronaut toy from the movie Toy Story");

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(characterController).build();
    }

    @Test
    public void getAllCharacters_success() throws Exception {
        List<Character> characters = new ArrayList<>(Arrays.asList(ch1,ch2));

        Mockito.when(characterRepository.findAll()).thenReturn(characters);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/characters")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect()
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(3))

        );
    }
     */
}
